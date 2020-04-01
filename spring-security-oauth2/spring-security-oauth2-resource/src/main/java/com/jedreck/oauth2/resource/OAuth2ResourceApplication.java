package com.jedreck.oauth2.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.jedreck.oauth2.**.dao")
public class OAuth2ResourceApplication {
    public static void main(String[] args) {
        // 无用户情况下 初始用户为 “user”  密码在log中“Using generated security password:”
        SpringApplication.run(OAuth2ResourceApplication.class, args);
    }
}
