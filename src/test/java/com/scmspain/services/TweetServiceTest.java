package com.scmspain.services;

import com.scmspain.application.services.BasicTweetService;
import com.scmspain.application.services.MetricService;
import com.scmspain.application.services.TweetRepository;
import com.scmspain.domain.TweetService;
import com.scmspain.infrastructure.database.TweetEntityManagerRepository;
import com.scmspain.infrastructure.database.entities.Tweet;
import com.scmspain.infrastructure.metrics.SpringActuatorMetricService;

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

    private static final String GUYBRUSH = "Guybrush Threepwood";
    private static final String PIRATE = "Pirate";
    private static final String VALID_MESSAGE = "I am Guybrush Threepwood, mighty pirate.";
    private static final String TOO_LONG_MESSAGE = "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.";

    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetService tweetService;

    @Before
    public void setUp() {
        this.entityManager = mock(EntityManager.class);
        TweetRepository tweetRepository = new TweetEntityManagerRepository(entityManager);

        this.metricWriter = mock(MetricWriter.class);
        MetricService metricService = new SpringActuatorMetricService(metricWriter);

        this.tweetService = new BasicTweetService(tweetRepository, metricService);
    }

    @Test
    public void shouldInsertANewTweet() {
        tweetService.publish(GUYBRUSH, VALID_MESSAGE);
        InOrder inOrder = inOrder(metricWriter, entityManager);
        inOrder.verify(metricWriter).increment(any(Delta.class));
        inOrder.verify(entityManager).persist(any(Tweet.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetPublisherIsNull() {
        this.tweetService.publish(null, VALID_MESSAGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetPublisherIsEmpty() {
        this.tweetService.publish("", VALID_MESSAGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetTextIsNull() {
        this.tweetService.publish(PIRATE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetTextIsEmpty() {
        this.tweetService.publish(PIRATE, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetTextLengthIsInvalid() {
        this.tweetService.publish(PIRATE, TOO_LONG_MESSAGE);
    }

}
