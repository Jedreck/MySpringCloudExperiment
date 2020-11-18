package com.jedreck.testspringcloudgateway.utils;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class FilterResponseUtil {
    /**
     * 自定义返回结果
     */
    public static Mono<Void> format(ServerHttpResponse response, HttpStatus httpStatus, String msg) {
        response.setStatusCode(httpStatus);
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(bodyDataBuffer));
    }
}
