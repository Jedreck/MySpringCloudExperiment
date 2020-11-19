package com.jedreck.testspringcloudgateway.route;

import com.jedreck.testspringcloudgateway.domain.bean.AuthBean;
import com.jedreck.testspringcloudgateway.utils.FilterRequestUtil;
import com.jedreck.testspringcloudgateway.utils.JsonUtil;
import com.jedreck.testspringcloudgateway.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

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

    /**
     * // 获取post请求参数没意义，放弃
     */
    @Deprecated
    public boolean predictPost(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        // 是否符合路由
        if (!request.getPath().subPath(0).value().toUpperCase().startsWith("/T1")) {
            return false;
        }

        // 获取是否含有key
        String body = FilterRequestUtil.getBodyFromRequest(request);
        if (StringUtils.isBlank(body)) {
            return false;
        }
        Map<String, Object> map = JsonUtil.json2Object(body, Map.class);
        if (null == map || StringUtils.isBlank((String) map.get("key"))) {
            return false;
        }
        AuthBean auth = (AuthBean) redisUtil.get((String) map.get("key"));
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
