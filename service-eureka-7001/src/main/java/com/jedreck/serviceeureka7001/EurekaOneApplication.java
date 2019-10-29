package com.jedreck.serviceeureka7001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaOneApplication.class, args);
    }
}

