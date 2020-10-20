package com.jedreck.tester2.controller;

import com.jedreck.tester2.entitys.PersonEntity;
import com.jedreck.tester2.service.Test1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test01")
public class Test01Controller {

    @Autowired
    private Test1Service test1Service;

    /**
     * 可测试
     * 同数据库同表
     */
    @PostMapping("/changeName")
    public Boolean changeName(@RequestBody PersonEntity person) {
        return test1Service.changeName(person);
    }

    /**
     * 可测试
     * 不同数据库
     */
    @PostMapping("/insertPerson")
    public boolean insertPerson(@RequestBody PersonEntity person) {
        return test1Service.insertPerson(person);
    }
}
