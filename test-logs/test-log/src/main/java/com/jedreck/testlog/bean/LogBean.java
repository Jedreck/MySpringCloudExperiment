package com.jedreck.testlog.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogBean {
    @Bean
    public String debugBean() {
        log.debug("这是一条 debug 的");
        return "debug";
    }

    @Bean
    public String infoBean() {
        log.info("这是一条 info 的");
        return "info";
    }

    @Bean
    public String errorBean() {
        log.error("这是一条 error 的");
        return "error";
    }

}
