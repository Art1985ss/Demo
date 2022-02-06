package com.art.demo.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionResponse extends DefaultHandlerExceptionResolver {
    @ExceptionHandler(value = {BindException.class})
    protected ResponseEntity<ExceptionResponse> bindException(final BindException exception, final WebRequest request) {
        final String description = request.getDescription(false);
        final BindingResult bindingResult = exception.getBindingResult();
        final List<String> messages = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    final String fieldName = fieldError.getField();
                    final Object rejectedValue = fieldError.getRejectedValue();
                    final String message = fieldError.getDefaultMessage();
                    return fieldName + " : '" + rejectedValue + "' -> " + message;
                }).toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse().setMessages(messages).setTimestamp(LocalDateTime.now()).setDescription(description));
    }

    @ExceptionHandler(value = {ApplicationException.class})
    protected ResponseEntity<ExceptionResponse> noEntityFoundException(final ApplicationException exception, final WebRequest webRequest) {
        final ExceptionResponse body = new ExceptionResponse()
                .setDescription(webRequest.getDescription(false))
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(exception.getStatus()).body(body);
    }

    @ExceptionHandler(value = {DataAccessException.class})
    protected ResponseEntity<ExceptionResponse> dataAccessException(final DataAccessException exception, final WebRequest webRequest) {
        final ExceptionResponse body = new ExceptionResponse()
                .setTimestamp(LocalDateTime.now())
                .setMessage(exception.getLocalizedMessage())
                .setDescription(webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
