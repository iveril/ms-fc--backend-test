package com.scmspain.infrastructure.configuration;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

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

}
