package com.jedreck.serviceprovider8003.dao;

import com.jedreck.serviceapi.entities.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDao {
    private List<Student> students;

    public List<Student> getAll() {
        return students;
    }

    public StudentDao() {
        students = new ArrayList<>();
        Student student = new Student(21, "Java");
        students.add(student);
        student = new Student(22, "Python");
        students.add(student);
        student = new Student(23, "C++");
        students.add(student);
        student = new Student(24, "JavaScript");
        students.add(student);
    }
}
