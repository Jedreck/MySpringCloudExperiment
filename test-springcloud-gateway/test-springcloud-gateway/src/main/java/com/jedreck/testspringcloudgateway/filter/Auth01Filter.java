package com.jedreck.testspringcloudgateway.filter;

import cn.hutool.core.date.DateUnit;
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

import java.util.Date;

@Slf4j
@Component
@RefreshScope
public class Auth01Filter implements GatewayFilter, Ordered {
    @Autowired
    AuthRoute authRoute;

    @Value("${config.filter01}")
    boolean on;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!on) {
            return chain.filter(exchange);
        }
        AuthBean authBean = authRoute.auth.get();

        Date nowDate = new Date();
        long between = DateUtil.between(DateUtil.beginOfDay(authBean.getStartDate()), DateUtil.beginOfDay(nowDate), DateUnit.DAY, false);
        if (null != authBean.getStartDate() && between < 0) {
            log.error("[服务网关]--访问服务 {} 未到开始日期，开始日期：{}---现在日期：{}",
                    authBean.getKey(),
                    DateUtil.formatDate(authBean.getEndDate()),
                    DateUtil.formatDate(nowDate));
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            return FilterResponseUtil.format(exchange.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\":\"服务未到开始日期\"}");
        }
        between = DateUtil.between(DateUtil.beginOfDay(nowDate), DateUtil.beginOfDay(authBean.getEndDate()), DateUnit.DAY, false);
        if (null != authBean.getEndDate() && between < 0) {
            log.error("[服务网关]--访问服务 {} 已过结束日期，结束日期：{}---现在日期：{}",
                    authBean.getKey(),
                    DateUtil.formatDate(authBean.getEndDate()),
                    DateUtil.formatDate(nowDate));
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            return FilterResponseUtil.format(exchange.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\":\"服务已过结束日期\"}");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
