package com.art.demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Login should be provided")
    private String username;
    @NotNull(message = "First name should be provided")
    private String firstName;
    @NotNull(message = "Last name should be provided")
    private String lastName;
    private int age;
    @NotNull(message = "Address should be provided to receive orders.")
    @OneToOne(cascade = {javax.persistence.CascadeType.PERSIST})
    private Address address;
}
