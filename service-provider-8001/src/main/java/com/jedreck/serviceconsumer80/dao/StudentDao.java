package com.jedreck.serviceconsumer80.dao;

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
        Student student = new Student(1, "黎明");
        students.add(student);
        student = new Student(1, "黎明");
        students.add(student);
        student = new Student(2, "大明");
        students.add(student);
        student = new Student(3, "肖华");
        students.add(student);
        student = new Student(4, "李玉刚");
        students.add(student);
    }
}
