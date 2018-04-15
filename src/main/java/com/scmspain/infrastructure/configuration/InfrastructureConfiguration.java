package com.scmspain.infrastructure.configuration;

import javax.persistence.EntityManager;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import com.scmspain.infrastructure.database.TweetRepository;

@Configuration
public class InfrastructureConfiguration {

    @Bean
    public TweetRepository getTweetRepository(final EntityManager entityManager) {
        return new TweetRepository(entityManager);
    }

    @Bean
    @ExportMetricWriter
    public MetricWriter getMetricWriter(MBeanExporter exporter) {
        return new JmxMetricWriter(exporter);
    }

}
