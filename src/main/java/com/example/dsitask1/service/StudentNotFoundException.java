package com.example.dsitask1.service;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Student not found: " + id);
    }
}
