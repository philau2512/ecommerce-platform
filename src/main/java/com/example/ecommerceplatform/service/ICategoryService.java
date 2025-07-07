package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Category;
import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    boolean existsById(Long id);

    // Các phương thức bổ sung nếu cần
    List<Category> findByNameContaining(String keyword);
}