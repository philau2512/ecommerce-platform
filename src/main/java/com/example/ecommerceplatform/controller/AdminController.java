package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.model.Order;
import com.example.ecommerceplatform.model.Product;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private CloudinaryService cloudinaryService;

    // Dashboard - Trang chủ admin
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) Integer year, Model model) {
        // Nếu không có năm được chọn, mặc định lấy năm hiện tại
        int selectedYear = (year != null) ? year : LocalDate.now().getYear();

        // Lấy thống kê doanh thu, số lượng đơn hàng
        Map<String, Object> stats = adminService.getDashboardStats(selectedYear);

        // Lấy danh sách đơn hàng gần đây (5 đơn gần nhất)
        List<Order> recentOrders = orderService.findRecentOrders(5);

        model.addAttribute("stats", stats);
        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("selectedYear", selectedYear);

        // Tạo danh sách các năm để hiển thị trong dropdown
        int currentYear = LocalDate.now().getYear();
        List<Integer> years = new ArrayList<>();
        for (int i = currentYear; i >= currentYear - 5; i--) {
            years.add(i);
        }
        model.addAttribute("years", years);

        return "admin/dashboard";
    }

    // QUẢN LÝ SẢN PHẨM
    @GetMapping("/products")
    public String listProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long category,
            @RequestParam(defaultValue = "newest") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Xác định kiểu sắp xếp
        String sortField = "createdAt";
        Sort.Direction direction = Sort.Direction.DESC;

        if ("price_asc".equals(sort)) {
            sortField = "price";
            direction = Sort.Direction.ASC;
        } else if ("price_desc".equals(sort)) {
            sortField = "price";
            direction = Sort.Direction.DESC;
        }

        // Tạo Pageable cho phân trang và sắp xếp
        Pageable pageable = PageRequest.of(page, size, direction, sortField);
        Page<Product> productPage;

        // Gọi các phương thức repository phù hợp
        if (keyword != null && !keyword.isEmpty()) {
            if (category != null) {
                productPage = productService.findByNameContainingIgnoreCaseAndCategoryId(keyword, category, pageable);
            } else {
                productPage = productService.findByNameContainingIgnoreCase(keyword, pageable);
            }
        } else if (category != null) {
            productPage = productService.findByCategoryId(category, pageable);
        } else {
            productPage = productService.findAll(pageable);
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("categories", adminService.getAllCategories());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedSort", sort);
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "admin/products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", adminService.getAllCategories());
        return "admin/product-form";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile) {
        product.setId(null);

        // Xử lý hình ảnh với Cloudinary
        if (!imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            product.setImageUrl(imageUrl); // Lưu URL trực tiếp
        }

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
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        Product existingProduct = productService.findById(id);

        // Cập nhật các trường từ form
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setCategory(product.getCategory());

        // Xử lý hình ảnh với Cloudinary
        if (!imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile);
            existingProduct.setImageUrl(imageUrl);
        }

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