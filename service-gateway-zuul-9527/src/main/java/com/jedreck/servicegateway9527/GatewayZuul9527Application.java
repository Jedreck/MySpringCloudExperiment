package com.jedreck.servicegateway9527;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GatewayZuul9527Application {
    public static void main(String args[]) {
        SpringApplication.run(GatewayZuul9527Application.class, args);
    }
}
