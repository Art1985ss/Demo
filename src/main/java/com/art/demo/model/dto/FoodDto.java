package com.art.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
public class FoodDto extends ProductDto {
    @Future(message = "Date should be in the future")
    @NotNull(message = "Expiration date should be present for food products")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
}

