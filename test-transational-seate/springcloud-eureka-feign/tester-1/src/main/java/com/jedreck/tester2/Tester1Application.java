package com.jedreck.tester2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 使用Seata
 * 开启eureka和feign
 */
@SpringBootApplication
@MapperScan(basePackages = "com.jedreck.**.dao")
@EnableDiscoveryClient
@EnableFeignClients
public class Tester1Application {
    public static void main(String[] args) {
        SpringApplication.run(Tester1Application.class, args);
    }
}
