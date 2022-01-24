package com.art.demo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductDto implements Serializable {
    private long id;
    @NotBlank(message = "Name for the product can't be blank.")
    @NotEmpty(message = "Name for the product can't be empty.")
    @NotNull(message = "Name for product should be given.")
    private String name;
    @Length(message = "Product manufacturer name should be at least 2 characters long.", min = 2)
    @NotNull(message = "Product manufacturer should be provided.")
    private String manufacturer;
    @Positive(message = "Product price should be positive number.")
    @NotNull(message = "Product should have price")
    private BigDecimal pricePerUnit;
    @PositiveOrZero(message = "Product discount can't be negative.")
    @Max(message = "Let's be reasonable...", value = 100)
    private BigDecimal discount;
}
