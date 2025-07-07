package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void updateStatus(Long id, String status) {
        Order order = findById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> findRecentOrders(int limit) {
        return orderRepository.findTop5ByOrderByOrderDateDesc();
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> findByOrderDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return orderRepository.findByOrderDateBetween(fromDate, toDate, pageable);
    }

    @Override
    public Page<Order> findByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<Order> findByOrderDateAfter(LocalDateTime fromDate, Pageable pageable) {
        return orderRepository.findByOrderDateAfter(fromDate, pageable);
    }

    @Override
    public Page<Order> findByStatusAndOrderDateAfter(String status, LocalDateTime fromDate, Pageable pageable) {
        return orderRepository.findByStatusAndOrderDateAfter(status, fromDate, pageable);
    }

    @Override
    public Page<Order> findByStatusAndOrderDateBetween(String status, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return orderRepository.findByStatusAndOrderDateBetween(status, fromDate, toDate, pageable);
    }


}