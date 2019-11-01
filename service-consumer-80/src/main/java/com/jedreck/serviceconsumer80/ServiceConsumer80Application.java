package com.jedreck.serviceconsumer80;

import com.jedreck.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
//启动服务的时候使用自己的配置
@RibbonClient(name = "${provider01.name}",configuration = MySelfRule.class)
public class ServiceConsumer80Application {
    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumer80Application.class, args);
    }
}

