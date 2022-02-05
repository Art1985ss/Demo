package com.art.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "authority")
    private List<Roles> authorities = new ArrayList<>();
    @OneToOne(cascade = {javax.persistence.CascadeType.PERSIST})
    private Address address;

    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
