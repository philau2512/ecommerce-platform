package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
