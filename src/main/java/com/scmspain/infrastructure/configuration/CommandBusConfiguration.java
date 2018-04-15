package com.scmspain.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scmspain.domain.TweetService;
import com.scmspain.domain.command.CommandBus;
import com.scmspain.domain.command.PublishTweetCommandHandler;
import com.scmspain.infrastructure.commandbus.Registry;
import com.scmspain.infrastructure.commandbus.SpringCommandBus;

/**
 * Command bus configuration.
 */
@Configuration
public class CommandBusConfiguration {

    @Bean
    public CommandBus getCommandBus(final ApplicationContext applicationContext) {
        return new SpringCommandBus(new Registry(applicationContext));
    }

    @Bean
    public PublishTweetCommandHandler getPublishTweetCommandHandler(@Qualifier("mainTweetService") final TweetService tweetService) {
        return new PublishTweetCommandHandler(tweetService);
    }

}
