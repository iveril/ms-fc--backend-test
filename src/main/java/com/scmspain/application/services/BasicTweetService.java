package com.scmspain.application.services;

import java.util.List;

import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;

/**
 * Basic tweet service implementation.
 */
public class BasicTweetService implements TweetService {

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
        if (publisher != null && publisher.length() > 0 && text != null && text.length() > 0 && text.length() < 140) {
            this.metricService.incrementPublishedTweets();
            this.tweetRepository.save(publisher, text);
        } else {
            throw new IllegalArgumentException("Tweet must not be greater than 140 characters");
        }
    }

}
