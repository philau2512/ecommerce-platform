package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}