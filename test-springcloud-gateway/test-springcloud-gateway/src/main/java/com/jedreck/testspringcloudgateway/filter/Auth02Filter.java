package com.jedreck.testspringcloudgateway.filter;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.jedreck.testspringcloudgateway.domain.bean.AuthBean;
import com.jedreck.testspringcloudgateway.route.AuthRoute;
import com.jedreck.testspringcloudgateway.utils.FilterResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RefreshScope
public class Auth02Filter implements GatewayFilter, Ordered {
    @Autowired
    AuthRoute authRoute;

    @Value("${config.filter02}")
    boolean on;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!on) {
            return chain.filter(exchange);
        }
        AuthBean authBean = authRoute.auth.get();
        LocalTime nowTime = LocalTime.now();
        LocalTime startTime = DateUtil.toLocalDateTime(authBean.getStartTime()).toLocalTime();
        LocalTime endTime = DateUtil.toLocalDateTime(authBean.getEndTime()).toLocalTime();
        if (null != authBean.getStartTime() && nowTime.isBefore(startTime)) {
            log.error("[服务网关]--访问服务 {} 未到开始时间，开始时间：{}---现在时间：{}",
                    authBean.getKey(),
                    DateUtil.formatTime(authBean.getStartTime()),
                    nowTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            return FilterResponseUtil.format(exchange.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\":\"服务未到开始时间\"}");
        }
        if (null != authBean.getEndTime() && nowTime.isAfter(endTime)) {
            log.error("[服务网关]--访问服务 {} 已过结束时间，结束时间：{}---现在时间：{}",
                    authBean.getKey(),
                    DateUtil.formatTime(authBean.getEndTime()),
                    nowTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            return FilterResponseUtil.format(exchange.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\":\"服务已过结束时间\"}");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
