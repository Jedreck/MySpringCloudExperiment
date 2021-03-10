package com.jedreck.websocket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWordController {
    @RequestMapping("")
    public String ping() {
        return "hello word";
    }
}
