package com.jedreck.serviceprovider8001.service.impl;

import com.jedreck.serviceapi.entities.Student;
import com.jedreck.serviceprovider8001.dao.StudentDao;
import com.jedreck.serviceprovider8001.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;

    @Override
    public List<Student> getAll() {
        return studentDao.getAll();
    }
}
