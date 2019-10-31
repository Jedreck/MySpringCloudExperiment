package com.jedreck.serviceeureka7002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author LanJun
 * 2019/10/31
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaTwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaTwoApplication.class, args);
    }
}
