package com.saar.service;

import java.util.List;

import com.saar.model.Student;

public interface StudentService {

    String createStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    String updateStudent(Long id, Student student);

    String deleteStudent(Long id);
}