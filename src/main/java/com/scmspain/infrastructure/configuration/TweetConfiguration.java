package com.scmspain.infrastructure.configuration;

import com.scmspain.infrastructure.controller.TweetController;
import com.scmspain.application.services.TweetService;
import com.scmspain.infrastructure.database.TweetRepository;

import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TweetConfiguration {

    @Bean
    public TweetService getTweetService(TweetRepository tweetRepository, MetricWriter metricWriter) {
        return new TweetService(tweetRepository, metricWriter);
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService) {
        return new TweetController(tweetService);
    }

}
