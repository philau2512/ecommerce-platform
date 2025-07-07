package com.example.ecommerceplatform.controller;
import com.example.ecommerceplatform.model.Product;
import com.example.ecommerceplatform.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private IProductRepository productRepository;

    @RequestMapping("")
    public String home(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String search, Model model) {
        Page<Product> products;
        if (StringUtils.hasText(search)) {
            products = productRepository.findByKeywordIncludingCategory(search.trim(), PageRequest.of(page, 8));
            model.addAttribute("searchKeyword", search.trim());
        } else {
            products = productRepository.findAll(PageRequest.of(page, 8));
        }
        model.addAttribute("products", products);
        return "home";
    }

    @RequestMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            return "product/detail";
        } else {
            // Product not found - redirect to home page or show error
            model.addAttribute("error", "Sản phẩm không tồn tại!");
            return "redirect:/"; // or return "error/404";
        }
    }
}