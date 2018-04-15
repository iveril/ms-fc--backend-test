package com.scmspain.infrastructure.configuration;

import com.scmspain.application.services.BasicTweetService;
import com.scmspain.application.services.MetricService;
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
        return new BasicTweetService(tweetRepository, metricService);
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService) {
        return new TweetController(tweetService);
    }

}
