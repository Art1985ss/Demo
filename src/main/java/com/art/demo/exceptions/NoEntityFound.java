package com.art.demo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoEntityFound extends ApplicationException {
    public <T> NoEntityFound(final String message, final Class<T> clazz) {
        super("No " + clazz.getSimpleName() + " found  by " + message, HttpStatus.NOT_FOUND);
    }
}
