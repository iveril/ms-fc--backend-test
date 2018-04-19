package com.scmspain.application.services;

import java.util.List;

import com.scmspain.domain.MetricService;
import com.scmspain.domain.TweetNotFoundException;
import com.scmspain.domain.TweetService;
import com.scmspain.domain.model.TweetResponse;

/**
 * Tweet service implementation that adds metric to another wrapped tweet service.
 */
public class TweetMetricService implements TweetService {

    private final MetricService metricService;
    private final TweetService tweetService;

    /**
     * Constructor.
     *
     * @param tweetService Tweet service to wrap.
     * @param metricService Metrics service.
     */
    public TweetMetricService(final TweetService tweetService, final MetricService metricService) {
        this.tweetService = tweetService;
        this.metricService = metricService;
    }

    @Override
    public Long publish(String publisher, String text) {
        metricService.incrementPublishedTweets();
        return tweetService.publish(publisher, text);
    }

    @Override
    public List<TweetResponse> listAll() {
        metricService.incrementTimesQueriedTweets();
        return tweetService.listAll();
    }

    @Override
    public void discard(Long tweetId) throws TweetNotFoundException {
        metricService.incrementDiscardedTweets();
        tweetService.discard(tweetId);
    }

    @Override
    public List<TweetResponse> listAllDiscarded() {
        metricService.incrementTimesQueriedDiscardedTweets();
        return tweetService.listAllDiscarded();
    }

}
