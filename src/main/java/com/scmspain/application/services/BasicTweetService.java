package com.scmspain.application.services;

import java.util.List;

import org.springframework.util.Assert;

import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;

/**
 * Basic tweet service implementation.
 */
public class BasicTweetService implements TweetService {

    private static final int MAX_CHARACTERS = 140;

    private final TweetRepository tweetRepository;
    private final MetricService metricService;

    /**
     * Constructor.
     *
     * @param tweetRepository Tweet repository.
     * @param metricService Metric service.
     */
    public BasicTweetService(final TweetRepository tweetRepository, final MetricService metricService) {
        this.tweetRepository = tweetRepository;
        this.metricService = metricService;
    }

    @Override
    public List<TweetResponse> listAll() {
        this.metricService.incrementTimesQueriedTweets();
        return tweetRepository.findAll();
    }

    @Override
    public void publish(final String publisher, final String text) {
        this.metricService.incrementPublishedTweets();
        Assert.isTrue(publisherIsNotEmpty(publisher), "Publisher must not be empty");
        Assert.isTrue(textIsNotEmpty(text), "Tweet must not be empty");
        Assert.isTrue(textNotTooLong(text), "Tweet must not be greater than " + MAX_CHARACTERS + " characters");
        this.tweetRepository.save(publisher, text);
    }

    private boolean textNotTooLong(String text) {
        return text.length() <= MAX_CHARACTERS;
    }

    private boolean textIsNotEmpty(String text) {
        return text != null && text.length() > 0;
    }

    private boolean publisherIsNotEmpty(String publisher) {
        return publisher != null && publisher.length() > 0;
    }

}
