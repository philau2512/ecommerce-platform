package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.CartItem;
import com.example.ecommerceplatform.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface ICartService {
    void addProductToCart(User user, Long productId, int quantity);

    List<CartItem> getCartItems(User user);

    BigDecimal calculateTotalAmount(List<CartItem> cartItems);
}
