package com.scmspain.application.services;

import java.util.List;

import com.scmspain.domain.MetricService;
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
    public List<TweetResponse> listAll() {
        metricService.incrementTimesQueriedTweets();
        return tweetService.listAll();
    }

    @Override
    public void publish(String publisher, String text) {
        metricService.incrementPublishedTweets();
        tweetService.publish(publisher, text);
    }

}
