package com.art.demo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Entity
@SuperBuilder
@Table(name = "food")
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Product {
    @Future(message = "Date should be in the future")
    @NotNull(message = "Expiration date should be present for food products")
    private LocalDate expirationDate;
}
