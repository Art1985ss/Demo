package com.art.demo.service;

public interface Validator<T> {
    //TODO https://www.baeldung.com/java-command-pattern The Command Pattern in Java
    void validate(T t);
}
