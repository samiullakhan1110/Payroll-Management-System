package com.sks.exception;

public class PayrollNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L; // Add this line

    public PayrollNotFoundException(String message) {
        super(message);
    }
}

