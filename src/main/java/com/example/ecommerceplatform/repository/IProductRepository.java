package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
