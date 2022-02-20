package com.art.demo.service;

import com.art.demo.model.dto.ElectronicsDto;
import com.art.demo.model.dto.ProductDto;
import com.art.demo.model.mapper.ProductMapperTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ElectronicsServiceTest {
    private final static ElectronicsDto ELECTRONICS_DTO = createTestElectronicsDto();
    private static final long ID = 1L;
    private final ProductService productService = mock(ProductService.class);

    private final ElectronicsService electronicsService;

    public ElectronicsServiceTest() {
        this.electronicsService = new ElectronicsService(productService);
    }

    @Test
    void create() {
        when(productService.create(ELECTRONICS_DTO)).thenReturn(ELECTRONICS_DTO);

        final ElectronicsDto actualResponse = electronicsService.create(ELECTRONICS_DTO);

        assertEquals(ELECTRONICS_DTO.getId(), actualResponse.getId());
        assertEquals(ELECTRONICS_DTO.getEnergyConsumption(), actualResponse.getEnergyConsumption());
    }

    @Test
    void update() {
        final ArgumentCaptor<ProductDto> productDtoArgumentCaptor = ArgumentCaptor.forClass(ProductDto.class);

        electronicsService.update(ELECTRONICS_DTO, ID);

        verify(productService).update(productDtoArgumentCaptor.capture(), eq(ID));
        assertEquals(ID, productDtoArgumentCaptor.getValue().getId());
    }

    @Test
    void findById() {
        when(productService.findById(ID)).thenReturn(ELECTRONICS_DTO);

        final ElectronicsDto electronicsDto = electronicsService.findById(ID);

        verify(productService).findById(ID);
        assertEquals(ID, electronicsDto.getId());
    }

    @Test
    void findAll() {
        when(productService.findAll()).thenReturn(List.of(ELECTRONICS_DTO, ProductMapperTest.createProductDto()));

        final List<ElectronicsDto> actualResponse = electronicsService.findAll();

        assertEquals(1, actualResponse.size());
    }

    @Test
    void deleteById() {

        electronicsService.deleteById(ID);

        verify(productService).deleteById(ID);
    }

    public static ElectronicsDto createTestElectronicsDto() {
        return ((ElectronicsDto) new ElectronicsDto()
                .setId(ID))
                .setEnergyConsumption(2000);
    }
}