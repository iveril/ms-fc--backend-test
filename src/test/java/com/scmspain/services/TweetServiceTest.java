package com.scmspain.services;

import com.scmspain.application.services.TweetService;
import com.scmspain.application.services.TweetRepository;
import com.scmspain.infrastructure.database.TweetEntityManagerRepository;
import com.scmspain.infrastructure.database.entities.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import javax.persistence.EntityManager;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class TweetServiceTest {

    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetService tweetService;

    @Before
    public void setUp() {
        this.entityManager = mock(EntityManager.class);
        TweetRepository tweetRepository = new TweetEntityManagerRepository(entityManager);
        this.metricWriter = mock(MetricWriter.class);
        this.tweetService = new TweetService(tweetRepository, metricWriter);
    }

    @Test
    public void shouldInsertANewTweet() {
        String publisher = "Guybrush Threepwood";
        String text = "I am Guybrush Threepwood, mighty pirate.";
        tweetService.publishTweet(publisher, text);
        InOrder inOrder = inOrder(metricWriter, entityManager);
        inOrder.verify(metricWriter).increment(any(Delta.class));
        inOrder.verify(entityManager).persist(any(Tweet.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() {
        tweetService.publishTweet("Pirate", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");
    }

}
