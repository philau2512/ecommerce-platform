package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/profile")
    public String viewProfile(Model model, java.security.Principal principal) {
        String username = principal.getName(); // ✅ Lấy username từ Spring Security
        User user = userService.findByUsername(username); // ✅ Tìm trong DB
        model.addAttribute("user", user);
        return "user/profile";
    }

}