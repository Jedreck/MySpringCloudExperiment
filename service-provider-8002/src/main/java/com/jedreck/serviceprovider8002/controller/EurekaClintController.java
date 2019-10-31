package com.jedreck.serviceprovider8002.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 发现入驻到eureka的微服务
 */
@Slf4j
@RestController
@RequestMapping("/eureka")
public class EurekaClintController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/getAllClient")
    public Object getAllClient() {
        List<String> list = discoveryClient.getServices();
        log.info("\nlist--" + list.toString());

        for (ServiceInstance serviceInstance : discoveryClient.getInstances(appName.toUpperCase())) {
            log.info("serviceInstance--" +
                    "host:" + serviceInstance.getHost() + "--" +
                    "service id:" + serviceInstance.getServiceId() + "--" +
                    "url:" + serviceInstance.getUri() + "--" +
                    "port:" + serviceInstance.getPort());
        }
        return discoveryClient;
    }

}
