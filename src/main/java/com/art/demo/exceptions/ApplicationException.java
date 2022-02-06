package com.art.demo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {
    private final HttpStatus status;

    public ApplicationException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }
}
