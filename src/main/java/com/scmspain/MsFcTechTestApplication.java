package com.scmspain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = { MsFcTechTestApplication.class })
public class MsFcTechTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFcTechTestApplication.class, args);
    }

}
