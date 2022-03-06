package com.art.demo.model.mapper;

import com.art.demo.model.Order;
import com.art.demo.model.Product;
import com.art.demo.model.User;
import com.art.demo.model.dto.OrderDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderMapperTest {
    private static final int ID = 1;
    private static final Map<String, String> TEST_DRO_PRODUCT_MAP = Collections.emptyMap();
    private static final Map<Product, BigDecimal> TEST_PRODUCT_MAP = new HashMap<>();
    private static final Order TEST_ORDER = createOrder();

    private static final OrderDto TEST_ORDER_DTO = createDtoOrder();


    @Test
    void fromDto() {
        final Order order = OrderMapper.fromDto(TEST_ORDER_DTO);
        assertAll(
                () -> assertEquals(ID, order.getId()),
                () -> assertEquals(0, order.getProductsMap().size()),
                () -> assertNull(order.getUser())
        );
    }

    @Test
    void toDto() {
        TEST_PRODUCT_MAP.put(ProductMapperTest.TEST_PRODUCT, new BigDecimal(10));
        final OrderDto orderDto = OrderMapper.toDto(TEST_ORDER.setProductsMap(TEST_PRODUCT_MAP));
        assertAll(
                () -> assertEquals(ID, orderDto.getId()),
                () -> assertEquals(new BigDecimal("500.00"), orderDto.getTotalPrice()),
                () -> assertEquals(1, orderDto.getProductMap().size())
        );
    }

    public static Order createOrder() {
        return new Order()
                .setId(ID)
                .setUser(new User())
                .setProductsMap(new HashMap<>());
    }

    public static OrderDto createDtoOrder() {
        return new OrderDto()
                .setId(ID)
                .setTotalPrice(new BigDecimal("10"))
                .setProductMap(TEST_DRO_PRODUCT_MAP);
    }
}