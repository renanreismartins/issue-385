package com.metrics.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cloud.aws.core.env.ec2.AmazonEc2InstanceDataPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Configuration
public class MetricsConfig {


    private final MeterRegistry registry;
    private final AwsEc2MicrometerMetricsProperties metricsProperties;
    private final AmazonEc2InstanceDataPropertySource ec2PropertySource;

    public MetricsConfig(MeterRegistry registry, AwsEc2MicrometerMetricsProperties metricsProperties) {
        this.registry = registry;
        this.metricsProperties = metricsProperties;
        this.ec2PropertySource = new AmazonEc2InstanceDataPropertySource("test");
    }

    @Bean
    public Counter personCounter() {
        return registry.counter("received.person");
    }

    @Bean
    public MeterRegistryCustomizer meterRegistryCustomizer() {
        Collection<Tag> tags = metricsProperties.getTags()
                .stream()
                .map(this::retrieveMetadataValue)
                .flatMap(Optional::stream)
                .collect(toList());

        return registry -> registry.config().commonTags(tags);
    }

    private Optional<ImmutableTag> retrieveMetadataValue(String tag) {
        return ofNullable(ec2PropertySource.getProperty(tag)).map(v -> new ImmutableTag(tag, (String) v));
    }

}
