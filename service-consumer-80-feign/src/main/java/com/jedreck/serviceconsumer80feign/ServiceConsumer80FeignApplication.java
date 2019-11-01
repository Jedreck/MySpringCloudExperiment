package com.jedreck.serviceconsumer80feign;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.jedreck"})
@ComponentScan("com.jedreck")
public class ServiceConsumer80FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumer80FeignApplication.class, args);
    }
}