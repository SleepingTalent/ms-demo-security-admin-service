package com.babcock.securityadmin.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan("com.babcock.securityadmin")
@Import({SecurityConfiguration.class,DatabaseConfiguration.class, SwaggerConfiguration.class})
public class CloudConfiguration {
}
