package com.jedreck.serviceprovider8011.dao;

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
        Student student = new Student(111, "黎明");
        students.add(student);
        student = new Student(112, "李华");
        students.add(student);
        student = new Student(113, "李云龙");
        students.add(student);
        student = new Student(114, "李涛");
        students.add(student);
        student = new Student(115, "李玉刚");
        students.add(student);
    }
}
