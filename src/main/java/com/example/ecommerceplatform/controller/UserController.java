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
    public String viewProfile(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        // Lấy thông tin user từ session
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập để xem trang này");
            return "redirect:/login";
        }

        // Lấy thông tin đầy đủ từ database (để đảm bảo thông tin mới nhất)
        User user = userService.findById(sessionUser.getId());
        model.addAttribute("user", user);

        return "user/profile";
    }
}