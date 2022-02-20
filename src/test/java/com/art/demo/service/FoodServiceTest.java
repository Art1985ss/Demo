package com.art.demo.service;

import com.art.demo.model.dto.FoodDto;
import com.art.demo.model.dto.ProductDto;
import com.art.demo.model.mapper.ProductMapperTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FoodServiceTest {
    private static final ProductDto TEST_PRODUCT_DTO = ProductMapperTest.createProductDto();
    private final ProductService productService = mock(ProductService.class);
    private final FoodService foodService;

    public FoodServiceTest() {
        this.foodService = new FoodService(productService);
    }

    @Test
    void create() {
        when(productService.create(TEST_PRODUCT_DTO)).thenReturn(TEST_PRODUCT_DTO);

        final ProductDto actualResponse = foodService.create(((FoodDto) TEST_PRODUCT_DTO));

        verify(productService).create(TEST_PRODUCT_DTO);
        assertEquals(TEST_PRODUCT_DTO.getId(), actualResponse.getId());
    }

    @Test
    void update() {

        foodService.update(((FoodDto) TEST_PRODUCT_DTO), TEST_PRODUCT_DTO.getId());

        verify(productService).update(TEST_PRODUCT_DTO, TEST_PRODUCT_DTO.getId());
    }

    @Test
    void findById() {
        when(productService.findById(TEST_PRODUCT_DTO.getId())).thenReturn(TEST_PRODUCT_DTO);

        final FoodDto actualResponse = foodService.findById(TEST_PRODUCT_DTO.getId());

        verify(productService).findById(TEST_PRODUCT_DTO.getId());
        assertEquals(TEST_PRODUCT_DTO.getId(), actualResponse.getId());
    }

    @Test
    void findAll() {
        when(productService.findAll()).thenReturn(List.of(ElectronicsServiceTest.createTestElectronicsDto(), TEST_PRODUCT_DTO));

        final List<FoodDto> actualResponse = foodService.findAll();

        verify(productService).findAll();
        assertEquals(1, actualResponse.size());
    }

    @Test
    void deleteById() {

        foodService.deleteById(TEST_PRODUCT_DTO.getId());

        verify(productService).deleteById(TEST_PRODUCT_DTO.getId());
    }
}