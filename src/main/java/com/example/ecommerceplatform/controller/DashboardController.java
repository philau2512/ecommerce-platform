package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
    private IAdminService adminService;

    @GetMapping("/stats")
    @ResponseBody
    public Map<String, Object> getStats() {
        return adminService.getDashboardStats();
    }

    @GetMapping("/revenue-stats")
    @ResponseBody
    public Map<String, Double> getRevenueStats() {
        return adminService.getRevenueByMonth();
    }

    @GetMapping("/order-stats")
    @ResponseBody
    public Map<String, Integer> getOrderStats() {
        return adminService.getOrderCountByMonth();
    }
}