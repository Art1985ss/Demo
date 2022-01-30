package com.art.demo.model.mapper;

import com.art.demo.model.Order;
import com.art.demo.model.dto.OrderDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {
    public static Order fromDto(final OrderDto orderDto) {
        return new Order()
                .setId(orderDto.getId());
    }

    public static OrderDto toDto(final Order order) {
        final BigDecimal totalPrice = order.getProductsMap().entrySet().stream()
                .map(entry -> entry.getKey().getPricePerUnit().multiply(entry.getValue()))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        return new OrderDto()
                .setId(order.getId())
                .setTotalPrice(totalPrice);
    }
}
