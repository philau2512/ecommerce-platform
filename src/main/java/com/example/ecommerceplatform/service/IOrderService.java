package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders();
}