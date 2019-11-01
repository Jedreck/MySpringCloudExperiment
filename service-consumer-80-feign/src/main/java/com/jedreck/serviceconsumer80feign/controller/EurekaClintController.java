package com.jedreck.serviceconsumer80feign.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 发现入驻到eureka的微服务
 */
@Slf4j
@RestController
@RequestMapping("/eureka")
public class EurekaClintController {
    @Autowired
    RestTemplate restTemplate;

    @Value("${provider01.url}" + "/eureka/getAllClient")
    private String providerUrl;

    @GetMapping("/getAllClient")
    public Object getAllClient() {
        return restTemplate.getForObject(providerUrl,Object.class);
    }

}
