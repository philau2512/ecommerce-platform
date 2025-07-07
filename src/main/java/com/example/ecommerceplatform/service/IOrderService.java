package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {
    List<Order> findAll();

    Order findById(Long id);

    Order save(Order order);

    void updateStatus(Long id, String status);

    List<Order> findByUserId(Long userId);

    // Phương thức mới để lấy đơn hàng gần đây
    List<Order> findRecentOrders(int limit);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByOrderDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    Page<Order> findByStatus(String status, Pageable pageable);

    Page<Order> findByOrderDateAfter(LocalDateTime fromDate, Pageable pageable);

    Page<Order> findByStatusAndOrderDateAfter(String status, LocalDateTime fromDate, Pageable pageable);

    Page<Order> findByStatusAndOrderDateBetween(String status, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

}