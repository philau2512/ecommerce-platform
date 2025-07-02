package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.User;

public interface ICartService {
    void addProductToCart(User user, Long productId, int quantity);
}

