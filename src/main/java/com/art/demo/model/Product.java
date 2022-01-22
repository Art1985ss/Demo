package com.art.demo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@ToString
@Immutable
@SuperBuilder
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @NotBlank(message = "Name for the product can't be blank.")
    @NotEmpty(message = "Name for the product can't be empty.")
    @NotNull(message = "Name for product should be given.")
    protected String name;
    @Length(message = "Product manufacturer name should be at least 2 characters long.", min = 2)
    @NotNull(message = "Product manufacturer should be provided.")
    protected String manufacturer;
    @Positive(message = "Product price should be positive number.")
    @NotNull(message = "Product should have price")
    protected BigDecimal pricePerUnit;
    @PositiveOrZero(message = "Product discount can't be negative.")
    protected BigDecimal discount;
}
