package com.jedreck.serviceprovider8003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author LanJun
 * 2019/10/31 19:02
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceProvider8003Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider8003Application.class, args);
    }
}