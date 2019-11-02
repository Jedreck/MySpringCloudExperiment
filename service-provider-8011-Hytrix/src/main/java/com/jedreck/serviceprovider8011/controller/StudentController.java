package com.jedreck.serviceprovider8011.controller;

import com.jedreck.serviceprovider8011.service.StudentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/student")

public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/getAll")
    @HystrixCommand(fallbackMethod = "getAllFallback")
    public List getAll() {
        if (LocalTime.now().getSecond() % 10 > 5) {
            throw new RuntimeException("抛出异常");
        }
        return studentService.getAll();
    }

    public List getAllFallback() {
        ArrayList<HashMap> arrayList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>(1);
        map.put("Hystri8011", "服务暂停，稍后再试");
        arrayList.add(map);
        return arrayList;
    }
}
