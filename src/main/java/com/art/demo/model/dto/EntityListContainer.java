package com.art.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EntityListContainer<T> {
    private final String title;
    private final List<T> list;
}
