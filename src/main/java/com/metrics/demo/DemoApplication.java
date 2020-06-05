package com.metrics.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
	public static final String AWS_EC2_METRICS_PROPERTY_PREFIX = "cloud2.aws.ec2.micrometer.metrics";

	@Bean
	@ConfigurationProperties(prefix = AWS_EC2_METRICS_PROPERTY_PREFIX)
	public AwsEc2MicrometerMetricsProperties awsEc2MicrometerMetricsProperties() {
		return new AwsEc2MicrometerMetricsProperties();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
