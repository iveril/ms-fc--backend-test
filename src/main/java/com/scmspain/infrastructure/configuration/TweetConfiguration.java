package com.scmspain.infrastructure.configuration;

import com.scmspain.application.services.TweetBasicService;
import com.scmspain.application.services.MetricService;
import com.scmspain.application.services.TweetMetricService;
import com.scmspain.domain.TweetService;
import com.scmspain.infrastructure.controller.TweetController;
import com.scmspain.application.services.TweetRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the tweet application.
 */
@Configuration
public class TweetConfiguration {

    @Bean
    public TweetService getTweetService(TweetRepository tweetRepository, MetricService metricService) {
        TweetService tweetBasicService = new TweetBasicService(tweetRepository);
        return new TweetMetricService(tweetBasicService, metricService);
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService) {
        return new TweetController(tweetService);
    }

}
