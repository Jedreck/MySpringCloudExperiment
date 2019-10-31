package com.jedreck.serviceprovider8002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author LanJun
 * 2019/10/31 18:59
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceProvider8002Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider8002Application.class, args);
    }
}
