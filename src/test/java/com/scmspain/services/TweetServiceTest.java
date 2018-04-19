package com.scmspain.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import com.scmspain.application.services.TweetMetricService;
import com.scmspain.application.services.TweetValidationService;
import com.scmspain.domain.MetricService;
import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;
import com.scmspain.infrastructure.repository.TweetRepository;
import com.scmspain.infrastructure.repository.entities.Tweet;
import com.scmspain.infrastructure.metrics.SpringActuatorMetricService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TweetServiceTest {

    private static final String GUYBRUSH = "Guybrush Threepwood";
    private static final String PIRATE = "Pirate";
    private static final String VALID_MESSAGE = "I am Guybrush Threepwood, mighty pirate.";
    private static final String TOO_LONG_MESSAGE = "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.";
    private static final String VALID_MESSAGE_WITH_URLS = "Link http 1 http://www.foogle.com - link https 1 https://www.foogle.com - link http 2 http://www.foogle.com - link https 2 https://www.foogle.com";

    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetService tweetService;

    @Before
    public void setUp() {
        this.entityManager = mock(EntityManager.class);
        TweetService tweetRepository = new TweetRepository(entityManager);

        this.metricWriter = mock(MetricWriter.class);
        MetricService metricService = new SpringActuatorMetricService(metricWriter);

        TweetService tweetValidationService = new TweetValidationService(tweetRepository);

        this.tweetService = new TweetMetricService(tweetValidationService, metricService);
    }

    @Test
    public void shouldInsertANewTweet() {
        this.tweetService.publish(GUYBRUSH, VALID_MESSAGE);
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

    @Test
    public void shouldNotCountUrlsForTweetLength() {
        this.tweetService.publish(GUYBRUSH, VALID_MESSAGE_WITH_URLS);
        InOrder inOrder = inOrder(metricWriter, entityManager);
        ArgumentCaptor<Delta> deltaArgument = ArgumentCaptor.forClass(Delta.class);
        inOrder.verify(metricWriter).increment(deltaArgument.capture());
        assertThat("published-tweets").isEqualTo(deltaArgument.getValue().getName());
        assertThat(1).isEqualTo(deltaArgument.getValue().getValue());
        inOrder.verify(entityManager).persist(any(Tweet.class));
    }

    @Test
    public void shouldListAllAvailableTweetsWithInvalidTweetIdentifiers() {
        Long idTweet1 = 9997L;
        Long idTweet2 = 9998L;
        Long idTweet3 = 9999L;
        mockEntityManagerQuery(idTweet1, idTweet2, idTweet3);

        Tweet tweet1 = tweet("publisher 9997", "content 9997");
        Tweet tweet3 = tweet("publisher 9999", "content 9999");
        mockEntityManagerFind(idTweet1, tweet1);
        mockEntityManagerFind(idTweet2, null);
        mockEntityManagerFind(idTweet3, tweet3);

        List<TweetResponse> tweets = this.tweetService.listAll();
        assertThat(tweets)
            .hasSize(2)
            .extracting(TweetResponse::getPublisher)
            .containsExactly("publisher 9997", "publisher 9999");

        ArgumentCaptor<Delta> deltaArgument = ArgumentCaptor.forClass(Delta.class);
        verify(metricWriter).increment(deltaArgument.capture());
        assertThat("times-queried-tweets").isEqualTo(deltaArgument.getValue().getName());
        assertThat(1).isEqualTo(deltaArgument.getValue().getValue());
    }

    private void mockEntityManagerQuery(Long... idTweet) {
        TypedQuery<Long> mockedQuery = mock(TypedQuery.class);
        when(mockedQuery.getResultList()).thenReturn(Arrays.asList(idTweet));
        when(entityManager.createQuery(any(String.class), any(Class.class))).thenReturn(mockedQuery);
    }

    private void mockEntityManagerFind(Long tweetId, Tweet tweet) {
        when(entityManager.find(Tweet.class, tweetId)).thenReturn(tweet);
    }

    private Tweet tweet(String publisher, String content) {
        return new Tweet(publisher, content, new Date());
    }

}
