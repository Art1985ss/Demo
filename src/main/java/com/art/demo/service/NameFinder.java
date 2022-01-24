package com.art.demo.service;

public interface NameFinder<T> {
    T findByName(final String name);
}
