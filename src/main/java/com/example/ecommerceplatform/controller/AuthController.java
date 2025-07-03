package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.Role;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.repository.IRoleRepository;
import com.example.ecommerceplatform.repository.IUserRepository;
import com.example.ecommerceplatform.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        // Thêm debug log
        System.out.println("Attempting login with: " + username);

        // Kiểm tra đăng nhập
        User user = userService.findByUsername(username);

        if (user == null) {
            System.out.println("User not found: " + username);
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "login";
        }

        // Debug password
        System.out.println("Stored password hash: " + user.getPassword());
        System.out.println("Password matches: " + passwordEncoder.matches(password, user.getPassword()));

        if (passwordEncoder.matches(password, user.getPassword())) {
            // Lưu user vào session (bỏ password ra khỏi session)
            User sessionUser = new User();
            sessionUser.setId(user.getId());
            sessionUser.setUsername(user.getUsername());
            sessionUser.setEmail(user.getEmail());
            sessionUser.setFullName(user.getFullName());
            sessionUser.setRole(user.getRole());

            session.setAttribute("user", sessionUser);

            // Chuyển hướng theo role
            System.out.println("Login successful. Role: " + user.getRole().getName());

            if ("ADMIN".equals(user.getRole().getName())) {
                return "redirect:/admin/dashboard";
            }

            return "redirect:/";
        }

        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user,
                                      @RequestParam(name = "confirmPassword") String confirmPassword,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {

        // Kiểm tra mật khẩu xác nhận
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp");
            return "register";
        }

        // Kiểm tra username đã tồn tại
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "register";
        }

        // Kiểm tra email đã tồn tại
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email đã được đăng ký");
            return "register";
        }

        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Thiết lập các thông tin mặc định
        user.setCreatedAt(LocalDateTime.now());

        // Thiết lập role là USER
        Role userRole = userService.findRoleByName("USER");
        user.setRole(userRole);

        // Lưu user
        userService.save(user);

        // Thông báo thành công
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}