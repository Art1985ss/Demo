package com.art.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class OrderDto implements Serializable {
    private long id;
    @JsonIgnore
    private UserDto user;
    private Map<ProductDto, BigDecimal> productMap = new HashMap<>();
    private BigDecimal totalPrice;
}
