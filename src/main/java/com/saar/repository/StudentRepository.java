package com.saar.repository;

import org.slf4j.MDC;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.saar.model.Student;
import com.saar.sql.StudentSql;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
@Slf4j
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Student> studentRowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student s = new Student();
            s.setId(rs.getLong("id"));
            s.setFirstName(rs.getString("first_name"));
            s.setLastName(rs.getString("last_name"));
            s.setEmail(rs.getString("email"));
            s.setEnrollmentDate(rs.getDate("enrollment_date").toLocalDate());
            s.setMajor(rs.getString("major"));
            return s;
        }
    };

    public int save(Student student) {
    	 MDC.put("layerType", "DB"); 
        log.info("Saving student: {}", student);
        int rows = jdbcTemplate.update(
                StudentSql.INSERT_STUDENT,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getMajor()
        );
        log.debug("Rows affected after save: {}", rows);
        MDC.put("layerType", "Application");
        return rows;
    }

    public List<Student> findAll() {
        log.info("Fetching all students");
        List<Student> students = jdbcTemplate.query(
                StudentSql.GET_ALL_STUDENTS,
                studentRowMapper
        );
        log.debug("Number of students fetched: {}", students.size());
        return students;
    }

    public Student findById(Long id) {
        log.info("Fetching student by id: {}", id);
        try {
            Student student = jdbcTemplate.queryForObject(
                    StudentSql.GET_STUDENT_BY_ID,
                    studentRowMapper,
                    id
            );
            log.debug("Student found: {}", student);
            return student;
        } catch (Exception e) {
            log.warn("No student found with id: {}", id, e);
            return null;
        }
    }

    public int update(Long id, Student student) {
        log.info("Updating student with id: {}", id);
        int rows = jdbcTemplate.update(
                StudentSql.UPDATE_STUDENT,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getMajor(),
                id
        );
        log.debug("Rows affected after update: {}", rows);
        return rows;
    }

    public int delete(Long id) {
        log.info("Deleting student with id: {}", id);
        int rows = jdbcTemplate.update(
                StudentSql.DELETE_STUDENT,
                id
        );
        log.debug("Rows affected after delete: {}", rows);
        return rows;
    }
}
