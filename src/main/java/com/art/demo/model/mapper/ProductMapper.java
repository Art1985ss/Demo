package com.art.demo.model.mapper;

import com.art.demo.model.Electronics;
import com.art.demo.model.Food;
import com.art.demo.model.Product;
import com.art.demo.model.dto.ElectronicsDto;
import com.art.demo.model.dto.FoodDto;
import com.art.demo.model.dto.ProductDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {
    public static Product fromDto(final ProductDto productDto) {
        Product product;
        if (productDto == null) return null;
        if (productDto instanceof FoodDto foodDto) {
            product = new Food()
                    .setExpirationDate(foodDto.getExpirationDate());
        } else if (productDto instanceof ElectronicsDto electronicsDto) {
            product = new Electronics()
                    .setEnergyConsumption(electronicsDto.getEnergyConsumption());
        } else return null;
        return product
                .setId(productDto.getId())
                .setName(productDto.getName())
                .setManufacturer(productDto.getManufacturer())
                .setPricePerUnit(productDto.getPricePerUnit())
                .setDiscount(productDto.getDiscount());
    }

    public static ProductDto toDto(final Product product) {
        ProductDto productDto;
        if (product == null) return null;
        if (product instanceof Food food) {
            productDto = new FoodDto()
                    .setExpirationDate(food.getExpirationDate());
        } else if (product instanceof Electronics electronics) {
            productDto = new ElectronicsDto()
                    .setEnergyConsumption(electronics.getEnergyConsumption());
        } else return null;
        return productDto
                .setId(product.getId())
                .setName(product.getName())
                .setManufacturer(product.getManufacturer())
                .setPricePerUnit(product.getPricePerUnit())
                .setDiscount(product.getDiscount());
    }
}
