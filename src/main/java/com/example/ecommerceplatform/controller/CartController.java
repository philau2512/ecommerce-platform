package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.CartItem;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.service.ICartService;
import com.example.ecommerceplatform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IUserService userService;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username);
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity) {

        User user = getCurrentUser();
        cartService.addProductToCart(user, productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        User user = getCurrentUser();

        List<CartItem> cartItems = cartService.getCartItems(user);
        BigDecimal totalAmount = cartService.calculateTotalAmount(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        return "cart/view";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("cartItemId") Long cartItemId) {
        User user = getCurrentUser();
        cartService.removeCartItem(user, cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCartItemQuantity(@RequestParam("cartItemId") Long cartItemId,
                                         @RequestParam("quantity") int quantity) {

        User user = getCurrentUser();
        cartService.updateCartItemQuantity(user, cartItemId, quantity);
        return "redirect:/cart";
    }
}
