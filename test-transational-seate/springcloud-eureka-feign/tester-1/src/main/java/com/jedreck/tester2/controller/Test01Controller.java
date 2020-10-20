package com.jedreck.tester2.controller;

import com.jedreck.tester2.service.Test1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test01")
public class Test01Controller {

    @Autowired
    private Test1Service test1Service;

    @GetMapping("/1")
    public Boolean test1() {
        return test1Service.step1();
    }

    @GetMapping("/2")
    public Boolean test2() {
        return test1Service.step2();
    }

    @GetMapping("/3")
    public Boolean test3() {
        return test1Service.step3();
    }
}
