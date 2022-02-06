package com.art.demo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class ElectronicsDto extends ProductDto {
    @Positive(message = "Energy consumption can't be less then zero.")
    private int energyConsumption;
}
