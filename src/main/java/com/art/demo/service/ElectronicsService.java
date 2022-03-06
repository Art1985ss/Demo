package com.art.demo.service;

import com.art.demo.model.dto.ElectronicsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectronicsService implements CRUD<ElectronicsDto> {
    private final ProductService productService;

    @Autowired
    public ElectronicsService(final ProductService productService) {
        this.productService = productService;
    }


    @Override
    public ElectronicsDto create(final ElectronicsDto electronicsDto) {
        return ((ElectronicsDto) productService.create(electronicsDto));
    }

    @Override
    public void update(final ElectronicsDto electronicsDto, final long id) {
        productService.update(electronicsDto, id);
    }

    @Override
    public ElectronicsDto findById(final long id) {
        return ((ElectronicsDto) productService.findById(id));
    }

    @Override
    public List<ElectronicsDto> findAll() {
        return productService.findAll().stream()
                .filter(ElectronicsDto.class::isInstance)
                .map(ElectronicsDto.class::cast)
                .toList();
    }

    @Override
    public void deleteById(final long id) {
        productService.deleteById(id);
    }
}
