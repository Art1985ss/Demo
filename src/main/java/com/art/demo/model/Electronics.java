package com.art.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "electronics")
@NoArgsConstructor
public class Electronics extends Product {
    @Positive(message = "Energy consumption can't be less then zero.")
    private int energyConsumption;
}
