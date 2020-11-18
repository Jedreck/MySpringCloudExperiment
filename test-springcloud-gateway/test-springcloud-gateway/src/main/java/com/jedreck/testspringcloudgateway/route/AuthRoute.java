package com.jedreck.testspringcloudgateway.route;

import com.jedreck.testspringcloudgateway.domain.bean.AuthBean;
import com.jedreck.testspringcloudgateway.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthRoute {

    public ThreadLocal<AuthBean> auth;
    @Autowired
    RedisUtil redisUtil;

    public boolean predictGet(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().subPath(0).value();
        // 是否符合路由
        if (!path.toUpperCase().startsWith("/T1")) {
            return false;
        }

        // 获取是否含有key
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        String key = queryParams.getFirst("key");
        AuthBean auth = (AuthBean) redisUtil.get(key);
        if (null == auth) {
            return false;
        }
        this.auth = new ThreadLocal<>();
        this.auth.set(auth);

        return true;
    }

    public String getUrl() {
        return "http://localhost:58001";
    }
}
