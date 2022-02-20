package com.art.demo.model.mapper;

import com.art.demo.model.Food;
import com.art.demo.model.Product;
import com.art.demo.model.dto.FoodDto;
import com.art.demo.model.dto.ProductDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {
    private static final long ID = 1;
    private static final String TEST_PRODUCT_NAME = "TestProductName";
    private static final String TEST_PRODUCT_MANUFACTURER = "TestProductManufacturer";
    private static final BigDecimal PRICE_PER_UNIT = new BigDecimal("100");
    private static final BigDecimal DISCOUNT = new BigDecimal(50);
    public static final Product TEST_PRODUCT = createProduct();
    private static final ProductDto TEST_DTO_PRODUCT = createProductDto();

    @Test
    void fromDto() {
        final Product product = ProductMapper.fromDto(TEST_DTO_PRODUCT);
        assertAll(
                () -> assertEquals(ID, product.getId()),
                () -> assertEquals(TEST_PRODUCT_NAME, product.getName()),
                () -> assertEquals(TEST_PRODUCT_MANUFACTURER, product.getManufacturer()),
                () -> assertEquals(PRICE_PER_UNIT, product.getPricePerUnit()),
                () -> assertEquals(DISCOUNT, product.getDiscount())
        );
    }

    @Test
    void toDto() {
        final ProductDto productDto = ProductMapper.toDto(TEST_PRODUCT);
        assertAll(
                () -> assertEquals(ID, productDto.getId()),
                () -> assertEquals(TEST_PRODUCT_NAME, productDto.getName()),
                () -> assertEquals(TEST_PRODUCT_MANUFACTURER, productDto.getManufacturer()),
                () -> assertEquals(PRICE_PER_UNIT, productDto.getPricePerUnit()),
                () -> assertEquals(DISCOUNT, productDto.getDiscount())
        );
    }

    public static Product createProduct() {
        return new Food()
                .setId(ID)
                .setName(TEST_PRODUCT_NAME)
                .setManufacturer(TEST_PRODUCT_MANUFACTURER)
                .setPricePerUnit(PRICE_PER_UNIT)
                .setDiscount(DISCOUNT);
    }

    public static ProductDto createProductDto() {
        return new FoodDto()
                .setId(ID)
                .setName(TEST_PRODUCT_NAME)
                .setManufacturer(TEST_PRODUCT_MANUFACTURER)
                .setPricePerUnit(PRICE_PER_UNIT)
                .setDiscount(DISCOUNT);
    }
}