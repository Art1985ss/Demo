package com.art.demo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@Entity
@Table(name = "electronics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Electronics extends Product {
    @Positive(message = "Energy consumption can't be less then zero.")
    private int energyConsumption;
}
