package com.art.demo.web.controller;

import com.art.demo.model.dto.EntityListContainer;
import com.art.demo.model.dto.ProductDto;
import com.art.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{value}")
    public ResponseEntity<EntityListContainer<ProductDto>> findByStringValue(@PathVariable String value) {
        final EntityListContainer<ProductDto> entityListContainer =
                new EntityListContainer<>("Products found", productService.findByStringValue(value));
        return ResponseEntity.ok(entityListContainer);
    }
}
