package com.art.demo.repository;

import com.art.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Long> {
}
