package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> findAll();
    Order findById(Long id);
    Order save(Order order);
    void updateStatus(Long id, String status);
    List<Order> findByUserId(Long userId);
    // Phương thức mới để lấy đơn hàng gần đây
    List<Order> findRecentOrders(int limit);
}