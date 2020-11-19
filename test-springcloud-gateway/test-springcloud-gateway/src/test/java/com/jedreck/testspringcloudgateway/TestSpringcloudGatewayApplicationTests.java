package com.jedreck.testspringcloudgateway;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.jedreck.testspringcloudgateway.domain.bean.AuthBean;
import com.jedreck.testspringcloudgateway.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class TestSpringcloudGatewayApplicationTests {
    @Autowired
    RedisUtil redisUtil;

    @Test
    void contextLoads() {
    }

    @Test
    void addAuth() {
        AuthBean authBean = new AuthBean();
        authBean.setKey("123456");
        authBean.setAuth01(100);
        authBean.setStartDate(DateUtil.parseDate("2020-11-19"));
        authBean.setEndDate(DateUtil.parseDate("2020-11-20"));
        authBean.setStartTime(DateUtil.parse("15:50:00", DatePattern.NORM_TIME_PATTERN));
        authBean.setEndTime(DateUtil.parse("19:30:00", DatePattern.NORM_TIME_PATTERN));
        redisUtil.set(authBean.getKey(), authBean);
    }

}
