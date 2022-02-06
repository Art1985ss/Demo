package com.art.demo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class AddressDto implements Serializable {
    private long id;
    @NotNull(message = "Country should be provided for address")
    private String country;
    @NotNull(message = "City should be provided for address")
    private String city;
    @NotNull(message = "Street name should be provided for Address")
    private String street;
    @Positive(message = "House number can't be negative or zero.")
    private int houseNumber;
    @Positive(message = "Apartment number can't be negative or zero.")
    private int apartmentNumber;
}
