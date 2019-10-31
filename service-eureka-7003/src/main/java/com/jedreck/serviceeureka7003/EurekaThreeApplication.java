package com.jedreck.serviceeureka7003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author LanJun
 * 2019/10/31
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaThreeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaThreeApplication.class, args);
    }
}