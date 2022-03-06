package com.art.demo.service;

import java.util.ArrayList;
import java.util.List;

public class ValidationService<T> implements Validator<T> {
    private final List<Validator<T>> validators = new ArrayList<>();

    public void addRule(Validator<T> validator) {
        validators.add(validator);
    }

    @Override
    public void validate(final T t) {
        validators.forEach(validator -> validator.validate(t));
    }
}
