package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}