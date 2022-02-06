package com.art.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class EntityMapContainer<K, V> {
    private final String title;
    private final Map<K, V> entityMap;
}
