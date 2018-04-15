package com.scmspain.infrastructure.configuration;

import com.scmspain.domain.MetricService;
import com.scmspain.application.services.TweetMetricService;
import com.scmspain.application.services.TweetValidationService;
import com.scmspain.domain.TweetService;
import com.scmspain.domain.command.CommandBus;
import com.scmspain.infrastructure.controller.TweetController;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the tweet application.
 */
@Configuration
public class TweetConfiguration {

    @Bean("mainTweetService")
    public TweetService getTweetService(@Qualifier("tweetRepository") TweetService tweetService, MetricService metricService) {
        TweetService tweetValidationService = new TweetValidationService(tweetService);
        return new TweetMetricService(tweetValidationService, metricService);
    }

    @Bean
    public TweetController getTweetConfiguration(final CommandBus commandBus, final @Qualifier("mainTweetService") TweetService tweetService) {
        return new TweetController(commandBus, tweetService);
    }

}
