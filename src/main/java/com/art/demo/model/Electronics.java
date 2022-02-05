package com.art.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Entity
@Table(name = "electronics")
@NoArgsConstructor
public class Electronics extends Product {
    @Positive(message = "Energy consumption can't be less then zero.")
    private int energyConsumption;

    @Override
    protected String type() {
        return "Electronics product:" + name;
    }
}
