package com.saar.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Student {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate enrollmentDate;
    private String major;
}