package com.art.demo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class FoodDto extends ProductDto {
    @Future(message = "Date should be in the future")
    @NotNull(message = "Expiration date should be present for food products")
    private LocalDate expirationDate;
}

