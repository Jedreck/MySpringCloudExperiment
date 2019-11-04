package com.jedreck.serviceapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author LanJun
 * 2019/11/1 14:29
 */
@FeignClient(value = "${provider01.name}", fallbackFactory = StudentClientServiceFallbackFactory.class)
public interface StudentClientService {
    @GetMapping(value = "provider/student/getAll")
    List getAll();
}
