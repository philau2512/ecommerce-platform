package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.User;

import java.util.List;

public interface IOrderService {
    Order placeOrder(User user);
    List<Order> getOrdersByUser(User user);
}
