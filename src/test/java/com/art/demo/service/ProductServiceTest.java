package com.art.demo.service;

import com.art.demo.exceptions.NoEntityFound;
import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.Product;
import com.art.demo.model.dto.ProductDto;
import com.art.demo.model.mapper.ProductMapperTest;
import com.art.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    private final static Product TEST_PRODUCT = ProductMapperTest.createProduct();
    private final static ProductDto TEST_PRODUCT_DTO = ProductMapperTest.createProductDto();
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductService productService;

    public ProductServiceTest() {
        productService = new ProductService(productRepository);
    }

    @Test
    void create() {
        final String productName = TEST_PRODUCT.getName();
        when(productRepository.findByName(productName)).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(TEST_PRODUCT);

        final ProductDto actualResponse = productService.create(TEST_PRODUCT_DTO);

        verify(productRepository).findByName(productName);
        verify(productRepository).save(any(Product.class));
        assertEquals(TEST_PRODUCT.getId(), actualResponse.getId());
    }

    @Test
    void createProductWithSuchNameAlreadyExists() {
        final String productName = TEST_PRODUCT.getName();
        when(productRepository.findByName(productName)).thenReturn(Optional.of(TEST_PRODUCT));
        when(productRepository.save(any(Product.class))).thenReturn(TEST_PRODUCT);

        assertThrows(ValidationException.class, () -> productService.create(TEST_PRODUCT_DTO));

        verify(productRepository).findByName(productName);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void update() {

        productService.update(TEST_PRODUCT_DTO, TEST_PRODUCT_DTO.getId());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void findById() {
        final Long testProductId = TEST_PRODUCT.getId();
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(TEST_PRODUCT));

        final ProductDto actualResponse = productService.findById(testProductId);

        verify(productRepository).findById(testProductId);
        assertEquals(testProductId, actualResponse.getId());
    }

    @Test
    void findByIdNotFound() {
        final Long testProductId = TEST_PRODUCT.getId();
        when(productRepository.findById(testProductId)).thenReturn(Optional.empty());

        assertThrows(NoEntityFound.class, () -> productService.findById(testProductId));

        verify(productRepository).findById(testProductId);
    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(List.of(TEST_PRODUCT));

        final List<ProductDto> actualResponse = productService.findAll();

        verify(productRepository).findAll();
        assertEquals(1, actualResponse.size());
        assertEquals(TEST_PRODUCT.getId(), actualResponse.get(0).getId());
    }

    @Test
    void deleteById() {
        final Long testProductId = TEST_PRODUCT.getId();
        productService.deleteById(testProductId);

        verify(productRepository).deleteById(testProductId);
    }

    @Test
    void findByName() {
        final String testProductName = TEST_PRODUCT.getName();
        when(productRepository.findByName(testProductName)).thenReturn(Optional.of(TEST_PRODUCT));

        final ProductDto actualResponse = productService.findByName(testProductName);

        verify(productRepository).findByName(testProductName);
        assertEquals(TEST_PRODUCT.getName(), actualResponse.getName());
        assertEquals(TEST_PRODUCT.getId(), actualResponse.getId());
    }

    @Test
    void findByNameNotFound() {
        final String testProductName = TEST_PRODUCT.getName();
        when(productRepository.findByName(testProductName)).thenReturn(Optional.empty());

        assertThrows(NoEntityFound.class, () -> productService.findByName(testProductName));

        verify(productRepository).findByName(testProductName);
    }

    @Test
    void findByStringValue() {
        final String value = TEST_PRODUCT.getManufacturer();
        when(productRepository.findByNameContainsOrManufacturerContainsIgnoreCase(value, value)).thenReturn(List.of(TEST_PRODUCT));

        final List<ProductDto> actualResponse = productService.findByStringValue(value);

        verify(productRepository).findByNameContainsOrManufacturerContainsIgnoreCase(value, value);
        assertEquals(1, actualResponse.size());
        assertEquals(TEST_PRODUCT.getManufacturer(), actualResponse.get(0).getManufacturer());
    }

    @Test
    void getById() {
        final Long testProductId = TEST_PRODUCT.getId();
        when(productRepository.findById(testProductId)).thenReturn(Optional.of(TEST_PRODUCT));

        final Product product = productService.getById(testProductId);

        verify(productRepository).findById(testProductId);
        assertEquals(TEST_PRODUCT.getId(), product.getId());
    }
}