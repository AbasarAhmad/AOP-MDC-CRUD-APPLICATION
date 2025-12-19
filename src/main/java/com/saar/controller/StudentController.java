package com.saar.controller;

import org.springframework.web.bind.annotation.*;

import com.saar.model.Student;
import com.saar.service.StudentService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
@RestController
@RequestMapping("/api/students")
@Slf4j
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public String createStudent(@RequestBody Student student) {
        log.info("Received request to create student: {}", student);
        String response = service.createStudent(student);
        log.debug("Response after creating student: {}", response);
        return response;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        log.info("Received request to fetch all students");
        List<Student> students = service.getAllStudents();
        log.debug("Number of students returned: {}", students.size());
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        log.info("Received request to fetch student with id: {}", id);
        Student student = service.getStudentById(id);
        if (student != null) {
            log.debug("Student found: {}", student);
        } else {
            log.warn("No student found with id: {}", id);
        }
        return student;
    }

    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
                                @RequestBody Student student) {
        log.info("Received request to update student with id: {}", id);
        String response = service.updateStudent(id, student);
        log.debug("Response after updating student: {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        log.info("Received request to delete student with id: {}", id);
        String response = service.deleteStudent(id);
        log.debug("Response after deleting student: {}", response);
        return response;
    }
}
