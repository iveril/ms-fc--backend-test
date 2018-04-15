package com.scmspain.infrastructure.configuration;

import javax.persistence.EntityManager;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import com.scmspain.application.services.MetricService;
import com.scmspain.application.services.TweetRepository;
import com.scmspain.infrastructure.database.TweetEntityManagerRepository;
import com.scmspain.infrastructure.metrics.SpringActuatorMetricService;

/**
 * Configuration for the infrastructure.
 */
@Configuration
public class InfrastructureConfiguration {

    @Bean
    @ExportMetricWriter
    public MetricWriter getMetricWriter(final MBeanExporter exporter) {
        return new JmxMetricWriter(exporter);
    }

    @Bean
    public MetricService getMetricService(final MetricWriter metricWriter) {
        return new SpringActuatorMetricService(metricWriter);
    }

    @Bean
    public TweetRepository getTweetRepository(final EntityManager entityManager) {
        return new TweetEntityManagerRepository(entityManager);
    }

}
