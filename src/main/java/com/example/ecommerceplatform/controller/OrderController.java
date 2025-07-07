package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.service.IOrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/place")
    public String placeOrder(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setId(1L);
            user.setUsername("Test User");
            session.setAttribute("user", user);
        }

        try {
            orderService.placeOrder(user);
            model.addAttribute("message", "Đặt hàng thành công!");
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/cart/view";
        }

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String viewOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setId(1L);
            user.setUsername("Test User");
            session.setAttribute("user", user);
        }

        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/order/checkout")
    public String checkout(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setId(1L);
            user.setUsername("Test User");
            session.setAttribute("user", user);
        }

        double totalAmount = 0.0;
        Object totalAmountObj = session.getAttribute("totalAmount");
        if (totalAmountObj != null) {
            totalAmount = (Double) totalAmountObj;
        }
        model.addAttribute("totalAmount", totalAmount);

        model.addAttribute("user", user);
        return "order/checkout";
    }

}
