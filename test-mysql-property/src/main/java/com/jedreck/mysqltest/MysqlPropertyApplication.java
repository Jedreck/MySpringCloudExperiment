package com.jedreck.mysqltest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.jedreck.**.dao")
public class MysqlPropertyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MysqlPropertyApplication.class, args);
    }
}
