package com.jedreck.nacossimple.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/t1")
@RefreshScope
public class TestController {

    @Value("${public.show-out:Local-789789}")
    private String showOut;
    @Value("${public.show-off:Local}")
    private String showOff;

    @GetMapping("/t1")
    public String t1() {
        return "{\"Show Out\":\"" + showOut + "\",\"Show Off\":\"" + showOff + "\"}";
    }
}
