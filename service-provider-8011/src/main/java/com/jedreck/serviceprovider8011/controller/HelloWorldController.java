package com.jedreck.serviceprovider8011.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HelloWorldController {
    @GetMapping()
    public Map getAll() {
        HashMap<String,String> map = new HashMap<>(1);
        map.put("8011","Hello World");
        return map;
    }
}
