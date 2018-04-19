package com.scmspain.infrastructure.metrics;

import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import com.scmspain.domain.MetricService;

/**
 * Metric service implementation based on spring actuator metric writer.
 */
@Service
public class SpringActuatorMetricService implements MetricService {

    private static final String PUBLISHED_TWEETS = "published-tweets";
    private static final String TIMES_QUERIED_TWEETS = "times-queried-tweets";
    private static final String DISCARDED_TWEETS = "discarded-tweets";
    private static final String TIMES_QUERIED_DISCARDED_TWEETS = "times-queried-discarded-tweets";

    private final MetricWriter metricWriter;

    /**
     * Constructor.
     *
     * @param metricWriter Metric writer.
     */
    public SpringActuatorMetricService(MetricWriter metricWriter) {
        this.metricWriter = metricWriter;
    }

    @Override
    public void incrementPublishedTweets() {
        metricWriter.increment(deltaOne(PUBLISHED_TWEETS));
    }

    @Override
    public void incrementTimesQueriedTweets() {
        metricWriter.increment(deltaOne(TIMES_QUERIED_TWEETS));
    }

    @Override
    public void incrementDiscardedTweets() {
        metricWriter.increment(deltaOne(DISCARDED_TWEETS));
    }

    @Override
    public void incrementTimesQueriedDiscardedTweets() {
        metricWriter.increment(deltaOne(TIMES_QUERIED_DISCARDED_TWEETS));
    }

    private Delta<Integer> deltaOne(String name) {
        return new Delta<>(name, 1);
    }

}
