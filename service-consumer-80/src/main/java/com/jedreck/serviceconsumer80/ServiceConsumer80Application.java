package com.jedreck.serviceconsumer80;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceConsumer80Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumer80Application.class, args);
    }
}

