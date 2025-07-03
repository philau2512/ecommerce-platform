package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Product findById(Long id);

    Product save(Product product);

    void deleteById(Long id);

    // Tìm kiếm
    Page<Product> findByNameContaining(String keyword, Pageable pageable);

    // Lọc theo danh mục
    List<Product> findByCategoryId(Long categoryId);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // Kết hợp tìm kiếm và lọc danh mục
    Page<Product> findByNameContainingAndCategoryId(String keyword, Long categoryId, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(String keyword, Long categoryId, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}