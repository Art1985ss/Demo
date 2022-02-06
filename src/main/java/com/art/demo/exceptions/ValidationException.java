package com.art.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends ApplicationException {
    public ValidationException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
