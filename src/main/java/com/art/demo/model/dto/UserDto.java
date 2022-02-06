package com.art.demo.model.dto;

import com.art.demo.model.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
    private long id;
    @NotEmpty(message = "Login should be provided")
    private String username;
    @NotEmpty(message = "First name should be provided")
    private String firstName;
    @NotEmpty(message = "Last name should be provided")
    private String lastName;
    @Min(value = 18, message = "User age should be at least 18 years old")
    private int age;
    @NotEmpty(message = "password value should be provided")
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Roles> authorities;
    @NotNull(message = "Address should be provided to receive orders.")
    private AddressDto address;
}
