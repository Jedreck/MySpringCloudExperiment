package com.jedreck.serviceprovider8002.dao;

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
        Student student = new Student(11, "周杰伦");
        students.add(student);
        student = new Student(12, "周大侠");
        students.add(student);
        student = new Student(13, "周思彤");
        students.add(student);
        student = new Student(14, "周天子");
        students.add(student);
        student = new Student(15, "周期表");
        students.add(student);
    }
}
