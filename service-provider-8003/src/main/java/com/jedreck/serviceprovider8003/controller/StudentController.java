package com.jedreck.serviceprovider8003.controller;

import com.jedreck.serviceapi.entities.Student;
import com.jedreck.serviceprovider8003.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/getAll")
    public List<Student> getAll() {
        return studentService.getAll();
    }
}
