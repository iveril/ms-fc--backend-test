package com.scmspain.infrastructure.configuration;

import com.scmspain.infrastructure.controller.TweetController;
import com.scmspain.application.services.TweetService;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class TweetConfiguration {

    @Bean
    public TweetService getTweetService(EntityManager entityManager, MetricWriter metricWriter) {
        return new TweetService(entityManager, metricWriter);
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService) {
        return new TweetController(tweetService);
    }

}
