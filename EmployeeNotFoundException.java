package com.sks.exception;

public class EmployeeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L; // Add this line

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
