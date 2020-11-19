package com.jedreck.testspringcloudgateway.config;

import com.jedreck.testspringcloudgateway.filter.Auth01Filter;
import com.jedreck.testspringcloudgateway.filter.Auth02Filter;
import com.jedreck.testspringcloudgateway.route.AuthRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class AllRoute {
    @Autowired
    AuthRoute authRoute;
    @Autowired
    Auth01Filter auth01Filter;
    @Autowired
    Auth02Filter auth02Filter;

    @Bean
    public RouteLocator AllRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.method(HttpMethod.GET)
                        .and()
                        .predicate(exchange -> authRoute.predictGet(exchange))
                        .filters(f -> f.filters(auth01Filter, auth02Filter))
                        .uri(authRoute.getUrl())
                )
                .build();
    }
}
