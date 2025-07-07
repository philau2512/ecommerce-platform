package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.CartItem;
import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.OrderItem;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.repository.ICartItemRepository;
import com.example.ecommerceplatform.repository.IOrderRepository;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
=======
>>>>>>> da325723564dcfadb2057ff812bf64c7e7f134b4
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

<<<<<<< HEAD
    @Autowired
    private IOrderRepository orderRepository;

=======
    private final IOrderRepository orderRepository;
>>>>>>> da325723564dcfadb2057ff812bf64c7e7f134b4
    private final ICartItemRepository cartItemRepository;

    public OrderService(IOrderRepository orderRepository, ICartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
<<<<<<< HEAD
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void updateStatus(Long id, String status) {
        Order order = findById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> findRecentOrders(int limit) {
        return orderRepository.findTop5ByOrderByOrderDateDesc();
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> findByOrderDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return orderRepository.findByOrderDateBetween(fromDate, toDate, pageable);
    }

    @Override
    public Page<Order> findByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<Order> findByOrderDateAfter(LocalDateTime fromDate, Pageable pageable) {
        return orderRepository.findByOrderDateAfter(fromDate, pageable);
    }

    @Override
    public Page<Order> findByStatusAndOrderDateAfter(String status, LocalDateTime fromDate, Pageable pageable) {
        return orderRepository.findByStatusAndOrderDateAfter(status, fromDate, pageable);
    }

    @Override
    public Page<Order> findByStatusAndOrderDateBetween(String status, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return orderRepository.findByStatusAndOrderDateBetween(status, fromDate, toDate, pageable);
    }

    @Override
=======
>>>>>>> da325723564dcfadb2057ff812bf64c7e7f134b4
    public Order placeOrder(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Giỏ hàng trống!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Đang xử lý");

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
<<<<<<< HEAD

}
=======
}
>>>>>>> da325723564dcfadb2057ff812bf64c7e7f134b4
