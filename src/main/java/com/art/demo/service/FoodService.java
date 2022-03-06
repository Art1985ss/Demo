package com.art.demo.service;

import com.art.demo.model.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService implements CRUD<FoodDto> {
    private final ProductService productService;

    @Autowired
    public FoodService(final ProductService productService) {
        this.productService = productService;
    }

    @Override
    public FoodDto create(final FoodDto foodDto) {
        return ((FoodDto) productService.create(foodDto));
    }

    @Override
    public void update(final FoodDto foodDto, final long id) {
        productService.update(foodDto, id);
    }

    @Override
    public FoodDto findById(final long id) {
        return ((FoodDto) productService.findById(id));
    }

    @Override
    public List<FoodDto> findAll() {
        return productService.findAll().stream()
                .filter(FoodDto.class::isInstance)
                .map(FoodDto.class::cast)
                .toList();
    }

    @Override
    public void deleteById(final long id) {
        productService.deleteById(id);
    }
}
