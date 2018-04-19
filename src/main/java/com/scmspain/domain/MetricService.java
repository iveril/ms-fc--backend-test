package com.scmspain.domain;

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

    /**
     * Increment by one the discarded tweets metrics.
     */
    void incrementDiscardedTweets();

    /**
     * Increment by one the times queried discarded tweets metrics.
     */
    void incrementTimesQueriedDiscardedTweets();

}
