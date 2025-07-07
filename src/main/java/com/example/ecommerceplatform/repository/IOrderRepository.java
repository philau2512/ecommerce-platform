package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserId(Long userId);

    // Phương thức để lấy đơn hàng gần đây
    List<Order> findTop5ByOrderByOrderDateDesc();

    Page<Order> findByOrderDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    Page<Order> findByStatus(String status, Pageable pageable);
    Page<Order> findByOrderDateAfter(LocalDateTime fromDate, Pageable pageable);

    Page<Order> findByStatusAndOrderDateAfter(String status, LocalDateTime fromDate, Pageable pageable);

    Page<Order> findByStatusAndOrderDateBetween(String status, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}