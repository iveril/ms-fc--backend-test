package com.scmspain.infrastructure.configuration;

import com.scmspain.domain.MetricService;
import com.scmspain.application.services.TweetMetricService;
import com.scmspain.application.services.TweetValidationService;
import com.scmspain.domain.TweetService;
import com.scmspain.domain.command.DiscardTweetCommandHandler;
import com.scmspain.domain.command.ListAllDiscardedTweetsCommandHandler;
import com.scmspain.domain.command.ListAllTweetsCommandHandler;
import com.scmspain.domain.command.PublishTweetCommandHandler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the tweet application.
 */
@Configuration
public class TweetConfiguration {

    @Bean
    public TweetService mainTweetService(final @Qualifier("tweetRepository") TweetService tweetService, final MetricService metricService) {
        TweetService tweetValidationService = new TweetValidationService(tweetService);
        return new TweetMetricService(tweetValidationService, metricService);
    }

    @Bean
    public PublishTweetCommandHandler getPublishTweetCommandHandler(@Qualifier("mainTweetService") final TweetService tweetService) {
        return new PublishTweetCommandHandler(tweetService);
    }

    @Bean
    public DiscardTweetCommandHandler getDiscardTweetCommandHandler(@Qualifier("mainTweetService") final TweetService tweetService) {
        return new DiscardTweetCommandHandler(tweetService);
    }

    @Bean
    public ListAllTweetsCommandHandler getListAllTweetsCommandHandler(@Qualifier("mainTweetService") final TweetService tweetService) {
        return new ListAllTweetsCommandHandler(tweetService);
    }

    @Bean
    public ListAllDiscardedTweetsCommandHandler getListAllDiscardedTweetsCommandHandler(@Qualifier("mainTweetService") final TweetService tweetService) {
        return new ListAllDiscardedTweetsCommandHandler(tweetService);
    }

}
