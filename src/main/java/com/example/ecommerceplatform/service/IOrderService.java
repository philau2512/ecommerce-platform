package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> findAll();
    Order findById(Long id);
    Order save(Order order);
    void updateStatus(Long id, String status);
    List<Order> findByUserId(Long userId);
}