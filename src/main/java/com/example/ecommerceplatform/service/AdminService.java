package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Category;
import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.repository.ICategoryRepository;
import com.example.ecommerceplatform.repository.IOrderRepository;
import com.example.ecommerceplatform.repository.IProductRepository;
import com.example.ecommerceplatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", getTotalOrders());
        stats.put("totalRevenue", getTotalRevenue());
        stats.put("totalCustomers", getTotalCustomers());
        stats.put("totalProducts", getTotalProducts());
        stats.put("revenueByMonth", getRevenueByMonth());
        stats.put("orderCountByMonth", getOrderCountByMonth());
        return stats;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Map<String, Double> getRevenueByMonth() {
        Map<String, Double> revenue = new HashMap<>();
        List<Order> orders = orderRepository.findAll();
        
        if (orders.isEmpty()) {
            return revenue;
        }
        
        Map<String, BigDecimal> revenueMap = new HashMap<>();
        
        // Tính tổng doanh thu theo tháng
        for (Order order : orders) {
            if (order.getOrderDate() != null) {
                String monthName = order.getOrderDate().getMonth().toString();
                BigDecimal currentRevenue = revenueMap.getOrDefault(monthName, BigDecimal.ZERO);
                revenueMap.put(monthName, currentRevenue.add(order.getTotalPrice()));
            }
        }
        
        // Chuyển đổi từ BigDecimal sang Double
        for (Map.Entry<String, BigDecimal> entry : revenueMap.entrySet()) {
            revenue.put(entry.getKey(), entry.getValue().doubleValue());
        }
        
        return revenue;
    }

    @Override
    public Map<String, Integer> getOrderCountByMonth() {
        Map<String, Integer> orderCounts = new HashMap<>();
        List<Order> orders = orderRepository.findAll();
        
        if (orders.isEmpty()) {
            return orderCounts;
        }
        
        // Sử dụng cách tiếp cận truyền thống thay vì Stream API phức tạp
        for (Order order : orders) {
            if (order.getOrderDate() != null) {
                String monthName = order.getOrderDate().getMonth().toString();
                Integer currentCount = orderCounts.getOrDefault(monthName, 0);
                orderCounts.put(monthName, currentCount + 1);
            }
        }
        
        return orderCounts;
    }

    @Override
    public int getTotalOrders() {
        return (int) orderRepository.count();
    }

    @Override
    public double getTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        BigDecimal total = BigDecimal.ZERO;
        
        for (Order order : orders) {
            total = total.add(order.getTotalPrice());
        }
        
        return total.doubleValue();
    }

    @Override
    public int getTotalCustomers() {
        long count = userRepository.count();
        // Kiểm tra nếu số lượng khách hàng vượt quá giới hạn Integer
        if (count > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) count;
    }

    @Override
    public int getTotalProducts() {
        return (int) productRepository.count();
    }
}