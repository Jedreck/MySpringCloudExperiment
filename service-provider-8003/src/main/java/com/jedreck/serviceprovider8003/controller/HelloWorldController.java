package com.jedreck.serviceprovider8003.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorldController {
    @GetMapping()
    public String getAll() {
        return "{\"8001\":\"Hello World\"}";
    }
}
