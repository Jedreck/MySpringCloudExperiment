package com.jedreck.serviceapi.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class StudentClientServiceFallbackFactory implements FallbackFactory<StudentClientService> {
    @Override
    public StudentClientService create(Throwable throwable) {
        return new StudentClientService() {
            @Override
            public List getAll() {
                ArrayList<HashMap> arrayList = new ArrayList<>();
                HashMap<String, String> map = new HashMap<>(1);
                map.put("接口", "服务暂停，稍后再试。。。");
                arrayList.add(map);
                return arrayList;
            }
        };
    }
}
