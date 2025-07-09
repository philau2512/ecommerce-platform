package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.repository.ICartItemRepository;
import com.example.ecommerceplatform.service.IOrderService;
import com.example.ecommerceplatform.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICartItemRepository cartItemRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username);
    }

    @PostMapping("/order/place")
    public String placeOrder(Model model) {
        User user = getCurrentUser();

        try {
            orderService.placeOrder(user);
            model.addAttribute("message", "Đặt hàng thành công!");
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/user/cart";
        }

        return "redirect:/user/orders";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        User user = getCurrentUser();
        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/order/checkout")
    public String checkout(Model model) {
        User user = getCurrentUser();

        double totalAmount = cartItemRepository.findByUser(user).stream()
                .mapToDouble(item -> item.getProduct().getPrice().doubleValue() * item.getQuantity())
                .sum();

        model.addAttribute("user", user);
        model.addAttribute("totalAmount", totalAmount);
        return "order/checkout";
    }
}
