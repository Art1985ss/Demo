package com.art.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = {javax.persistence.CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ElementCollection
    @CollectionTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))
    @MapKeyJoinColumn(table = "products", name = "product_id", referencedColumnName = "id")
    @Column(name = "amount")
    private Map<Product, BigDecimal> productsMap = new HashMap<>();
}
