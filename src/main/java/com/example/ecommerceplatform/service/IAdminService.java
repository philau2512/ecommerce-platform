package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Category;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    // Lấy thống kê cho dashboard
    Map<String, Object> getDashboardStats();

    // Lấy tất cả danh mục sản phẩm
    List<Category> getAllCategories();

    // Các phương thức báo cáo doanh thu
    Map<String, Double> getRevenueByMonth();

    Map<String, Integer> getOrderCountByMonth();

    // Tổng quan thống kê
    int getTotalOrders();

    double getTotalRevenue();

    int getTotalCustomers();

    int getTotalProducts();
}