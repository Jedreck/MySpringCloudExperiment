package com.jedreck.serviceconsumer80.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    RestTemplate restTemplate;

    @Value("${provider01.url}" + "/student/getAll")
    String providerUrl;

    @GetMapping("/getAll")
    public List getAll() {
        return restTemplate.getForObject(providerUrl, List.class);
    }
}
