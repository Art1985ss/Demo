package com.art.demo.service;

import com.art.demo.exceptions.NoEntityFound;
import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.Product;
import com.art.demo.model.dto.ProductDto;
import com.art.demo.model.mapper.ProductMapper;
import com.art.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.art.demo.model.mapper.ProductMapper.fromDto;
import static com.art.demo.model.mapper.ProductMapper.toDto;

@Service
public class ProductService implements CRUD<ProductDto>, NameFinder<ProductDto> {
    protected final ProductRepository productRepository;
    private final Validator<Product> productValidator;

    @Autowired
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
        productValidator = product -> productRepository.findByName(product.getName()).ifPresent(prod -> {
            throw new ValidationException("Product with name " + prod.getName() + " already exists in database");
        });
    }

    @Override
    public ProductDto create(final ProductDto productDto) {
        final Product productToSave = fromDto(productDto);
        productValidator.validate(productToSave);
        return toDto(productRepository.save(productToSave));
    }

    @Override
    public void update(final ProductDto productDto, final long id) {
        final Product product = fromDto(productDto).setId(id);
        productValidator.validate(product);
        productRepository.save(product);
    }

    @Override
    public ProductDto findById(final long id) {
        return toDto(productRepository.findById(id)
                             .orElseThrow(() -> new NoEntityFound("id " + id, Product.class)));
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(ProductMapper::toDto).toList();
    }

    @Override
    public void delete(final ProductDto productDto) {
        productRepository.delete(fromDto(productDto));
    }

    @Override
    public void deleteById(final long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new NoEntityFound("id " + id, Product.class);
        }
    }

    @Override
    public ProductDto findByName(final String name) {
        return toDto(productRepository.findByName(name)
                             .orElseThrow(() -> new NoEntityFound("name " + name, Product.class)));
    }
}
