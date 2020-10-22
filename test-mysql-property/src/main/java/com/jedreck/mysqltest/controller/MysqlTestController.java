package com.jedreck.mysqltest.controller;

import com.jedreck.mysqltest.service.MysqlTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mysql")
public class MysqlTestController {
    @Autowired
    MysqlTestService service;

    @GetMapping("/1")
    public boolean insert1() {
        service.insert();
        return true;
    }

    @GetMapping("/2")
    public boolean insert2() {
        service.insert2();
        return true;
    }
}
