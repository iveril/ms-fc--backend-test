package com.scmspain.infrastructure.configuration;

import javax.persistence.EntityManager;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import com.scmspain.domain.MetricService;
import com.scmspain.domain.TweetService;
import com.scmspain.domain.command.CommandBus;
import com.scmspain.infrastructure.commandbus.Registry;
import com.scmspain.infrastructure.commandbus.SpringCommandBus;
import com.scmspain.infrastructure.repository.TweetRepository;
import com.scmspain.infrastructure.metrics.SpringActuatorMetricService;

/**
 * Configuration for the infrastructure.
 */
@Configuration
public class InfrastructureConfiguration {

    @Bean
    public CommandBus getCommandBus(final ApplicationContext applicationContext) {
        return new SpringCommandBus(new Registry(applicationContext));
    }

    @Bean
    @ExportMetricWriter
    public MetricWriter getMetricWriter(final MBeanExporter exporter) {
        return new JmxMetricWriter(exporter);
    }

    @Bean
    public MetricService getMetricService(final MetricWriter metricWriter) {
        return new SpringActuatorMetricService(metricWriter);
    }

    @Bean("tweetRepository")
    public TweetService getTweetRepository(final EntityManager entityManager) {
        return new TweetRepository(entityManager);
    }

}
