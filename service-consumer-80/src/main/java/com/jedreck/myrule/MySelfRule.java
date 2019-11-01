package com.jedreck.myrule;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LanJun
 * 2019/11/1 11:14
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        return new FirstRule(7);
    }
}
