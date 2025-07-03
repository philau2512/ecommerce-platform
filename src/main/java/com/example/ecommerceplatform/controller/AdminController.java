package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.Product;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.service.IAdminService;
import com.example.ecommerceplatform.service.IOrderService;
import com.example.ecommerceplatform.service.IProductService;
import com.example.ecommerceplatform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    // Dashboard - Trang chủ admin
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Lấy thống kê doanh thu, số lượng đơn hàng
        Map<String, Object> stats = adminService.getDashboardStats();
        model.addAttribute("stats", stats);
        return "admin/dashboard";
    }

    // QUẢN LÝ SẢN PHẨM
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", adminService.getAllCategories());
        return "admin/product-form";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", adminService.getAllCategories());
        return "admin/product-form";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    // QUẢN LÝ ĐƠN HÀNG
    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "admin/order-detail";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }

    // QUẢN LÝ KHÁCH HÀNG
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAllCustomers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}