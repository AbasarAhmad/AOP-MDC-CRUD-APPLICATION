package com.saar.serviceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.saar.model.Student;
import com.saar.repository.StudentRepository;
import com.saar.service.StudentService;

import java.util.List;
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createStudent(Student student) {
        log.info("Creating student: {}", student);
        try {
            repository.save(student);
            log.debug("Student saved in repository: {}", student);
            return "Student created successfully";
        } catch (Exception e) {
            log.error("Error while creating student: {}", student, e);
            throw e;
        }
    }

    @Override
    public List<Student> getAllStudents() {
        log.info("Fetching all students");
        List<Student> students = repository.findAll();
        log.debug("Number of students fetched: {}", students.size());
        return students;
    }

    @Override
    public Student getStudentById(Long id) {
        log.info("Fetching student by id: {}", id);
        Student student = repository.findById(id);
        if (student != null) {
            log.debug("Student found: {}", student);
        } else {
            log.warn("No student found with id: {}", id);
        }
        return student;
    }

    @Override
    public String updateStudent(Long id, Student student) {
        log.info("Updating student with id: {}", id);
        try {
            repository.update(id, student);
            log.debug("Student updated: {}", student);
            return "Student updated successfully";
        } catch (Exception e) {
            log.error("Error while updating student with id: {}", id, e);
            throw e;
        }
    }

    @Override
    public String deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        try {
            repository.delete(id);
            log.debug("Student deleted with id: {}", id);
            return "Student deleted successfully";
        } catch (Exception e) {
            log.error("Error while deleting student with id: {}", id, e);
            throw e;
        }
    }
}
