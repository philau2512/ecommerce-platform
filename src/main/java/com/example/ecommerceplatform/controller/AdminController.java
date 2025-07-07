package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.service.IOrderService;
import com.example.ecommerceplatform.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final IOrderService orderService;
    private final IUserService userService;

    public AdminController(IOrderService orderService, IUserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public String viewAllOrders(Model model) {
        User user = new User();
        user.setId(1L);

        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        User user = new User();
        user.setId(1L);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam("userId") Long userId) {
        User user = new User();
        user.setId(1L); // Hard-code admin user

        userService.deleteUser(userId);
        return "redirect:/admin/users";
    }
}