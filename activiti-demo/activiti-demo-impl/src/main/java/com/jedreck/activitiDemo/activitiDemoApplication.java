package com.jedreck.activitiDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.jedreck.oauth2.**.dao")
public class activitiDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(activitiDemoApplication.class, args);
    }
}
