package com.jedreck.serviceconsumer80feign.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    //负载均衡 Ribbon
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule myRule() {
        //Ribbon 使用默认的轮询
//        return new RoundRobinRule();
        //Ribbon 使用随机替代默认的轮询
//        return new RandomRule();
        return new RetryRule();
//        return new WeightedResponseTimeRule();
    }
}
