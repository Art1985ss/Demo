package com.art.demo.repository;

import com.art.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(final String name);

    List<Product> findByNameContainsOrManufacturerContainsIgnoreCase(final String value1, final String value2);
}
