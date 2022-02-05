package com.art.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OrderDto {
    private long id;
    private Map<String, String> productMap = new HashMap<>();
    private BigDecimal totalPrice;
}
