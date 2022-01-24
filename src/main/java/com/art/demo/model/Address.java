package com.art.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Country should be provided for address")
    private String country;
    @NotNull(message = "City should be provided for address")
    private String city;
    @NotNull(message = "Street name should be provided for Address")
    private String street;
    @Positive(message = "House number can't be negative or zero.")
    @Column(nullable = false)
    private int houseNumber;
    @Positive(message = "Apartment number can't be negative or zero.")
    @Column(nullable = false)
    private int apartmentNumber;
}
