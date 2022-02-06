package com.art.demo.service;

import java.util.List;

public interface CRUD<T> {
    T create(final T t);

    void update(final T t,final long id);

    T findById(final long id);

    List<T> findAll();

    void delete(final T t);

    void deleteById(final long id);
}
