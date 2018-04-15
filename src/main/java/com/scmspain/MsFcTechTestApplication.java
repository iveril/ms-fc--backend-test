package com.scmspain;

import com.scmspain.infrastructure.configuration.CommandBusConfiguration;
import com.scmspain.infrastructure.configuration.InfrastructureConfiguration;
import com.scmspain.infrastructure.configuration.TweetConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({TweetConfiguration.class, InfrastructureConfiguration.class, CommandBusConfiguration.class})
public class MsFcTechTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFcTechTestApplication.class, args);
    }

}
