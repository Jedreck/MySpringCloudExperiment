package com.jedreck.serviceconsumer80.config;

        import org.springframework.cloud.client.loadbalancer.LoadBalanced;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    //负载均衡 Ribbon
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
