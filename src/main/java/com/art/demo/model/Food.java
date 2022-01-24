package com.art.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ToString(callSuper = true)
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "food")
@NoArgsConstructor
public class Food extends Product {
    @Future(message = "Date should be in the future")
    @NotNull(message = "Expiration date should be present for food products")
    private LocalDate expirationDate;
}
