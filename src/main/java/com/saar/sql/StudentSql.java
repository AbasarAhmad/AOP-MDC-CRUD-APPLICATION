package com.saar.sql;

public class StudentSql {

    public static final String INSERT_STUDENT =
            "INSERT INTO student (first_name, last_name, email, major) " +
            "VALUES (?, ?, ?, ?)";

    public static final String GET_ALL_STUDENTS =
            "SELECT * FROM student";

    public static final String GET_STUDENT_BY_ID =
            "SELECT * FROM student WHERE id = ?";

    public static final String UPDATE_STUDENT =
            "UPDATE student SET first_name=?, last_name=?, email=?, major=? WHERE id=?";

    public static final String DELETE_STUDENT =
            "DELETE FROM student WHERE id = ?";
}
