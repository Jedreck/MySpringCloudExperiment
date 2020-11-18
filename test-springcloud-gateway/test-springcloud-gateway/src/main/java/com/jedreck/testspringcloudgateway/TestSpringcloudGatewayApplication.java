package com.jedreck.testspringcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关
 * AuthRoute 由 nacos 动态配置，达到动态调整filter的效果
 */
@SpringBootApplication
public class TestSpringcloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringcloudGatewayApplication.class, args);
	}

}
