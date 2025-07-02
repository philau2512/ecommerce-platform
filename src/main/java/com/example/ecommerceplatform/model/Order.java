package com.example.ecommerceplatform.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date")
    @CreationTimestamp
    private LocalDateTime orderDate;

    private String status; // 'PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED'

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
