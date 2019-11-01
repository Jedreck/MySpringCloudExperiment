package com.jedreck.serviceconsumer80feign.controller;

import com.jedreck.serviceapi.service.StudentClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentClientService service;

    @GetMapping("/getAll")
    public List getAll() {
        return service.getAll();
    }
}
