package com.jedreck.serviceprovider8011;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class ServiceProvider8011HytrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider8011HytrixApplication.class, args);
    }
}
