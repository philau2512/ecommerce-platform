package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.CartItem;
import com.example.ecommerceplatform.model.Product;
import com.example.ecommerceplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUserAndProduct(User user, Product product);
}

