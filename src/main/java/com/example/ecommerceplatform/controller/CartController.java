package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.CartItem;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity) {
        
        User user = new User();
        user.setId(1L);

        cartService.addProductToCart(user, productId, quantity);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {

        User user = new User();
        user.setId(1L);

        List<CartItem> cartItems = cartService.getCartItems(user);
        BigDecimal totalAmount = cartService.calculateTotalAmount(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);

        return "cart/view";
    }
}
