package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Category;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    // Lấy thống kê cho dashboard
    Map<String, Object> getDashboardStats();

    // Lấy thống kê cho dashboard theo năm
    Map<String, Object> getDashboardStats(int year);

    // Lấy tất cả danh mục sản phẩm
    List<Category> getAllCategories();

    // Các phương thức báo cáo doanh thu
    Map<String, Double> getRevenueByMonth();

    // Các phương thức báo cáo doanh thu theo năm
    Map<String, Double> getRevenueByMonth(int year);

    Map<String, Integer> getOrderCountByMonth();

    Map<String, Integer> getOrderCountByMonth(int year);

    // Tổng quan thống kê
    int getTotalOrders();

    double getTotalRevenue();

    int getTotalCustomers();

    int getTotalProducts();
}