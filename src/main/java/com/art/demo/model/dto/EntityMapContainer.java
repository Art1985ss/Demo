package com.art.demo.model.dto;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class EntityMapContainer<K, V> {
    private final String title;
    private final Map<K, V> entityMap;
}
