package com.art.demo.web.controller;

import com.art.demo.model.dto.EntityListContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Validated
public interface ControllerCrud<T> {
    @PostMapping
    RedirectView create(@Valid @RequestBody final T t);

    @PutMapping("/{id}")
    RedirectView update(@Valid @RequestBody final T t, @PathVariable final long id);

    @GetMapping("/{id}")
    ResponseEntity<T> findById(@PathVariable final long id);

    @GetMapping
    ResponseEntity<EntityListContainer<T>> findAll();

    @DeleteMapping("/{id}")
    RedirectView deleteById(@PathVariable final long id);
}
