package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // name, descript
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // them category
    @Query("SELECT p FROM Product p LEFT JOIN p.category c WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findByKeywordIncludingCategory(@Param("keyword") String keyword, Pageable pageable);
}
