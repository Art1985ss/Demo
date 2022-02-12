package com.art.demo.model.mapper;

import com.art.demo.model.Order;
import com.art.demo.model.Product;
import com.art.demo.model.dto.OrderDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {

    public static Order fromDto(final OrderDto orderDto) {
        if (orderDto == null) return null;
        return new Order()
                .setId(orderDto.getId());
    }

    public static OrderDto toDto(final Order order) {
        if (order == null) return null;
        final BigDecimal totalPrice = order.getProductsMap().entrySet().stream()
                .map(entry -> entry.getKey().getTotalPrice().multiply(entry.getValue()))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        return new OrderDto()
                .setId(order.getId())
                .setTotalPrice(totalPrice)
                .setProductMap(order.getProductsMap().entrySet().stream()
                                       .collect(toMap(entry -> entry.getKey().getType(), valueMapper)));
    }

    private static final Function<Map.Entry<Product, BigDecimal>, String> valueMapper = entry -> {
        final String amount = "amount : " + entry.getValue();
        final BigDecimal price = entry.getKey().getTotalPrice().multiply(entry.getValue());
        return amount + ", total price: " + price;
    };
}
