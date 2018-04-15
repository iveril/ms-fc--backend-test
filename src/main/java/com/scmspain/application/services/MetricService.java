package com.scmspain.application.services;

/**
 * Metric service.
 */
public interface MetricService {

    /**
     * Increment by one the published tweets metrics.
     */
    void incrementPublishedTweets();

    /**
     * Increment by one the times queried tweets metrics.
     */
    void incrementTimesQueriedTweets();

}
