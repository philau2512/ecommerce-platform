package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.dto.ProductDto;
import com.example.ecommerceplatform.model.Category;
import com.example.ecommerceplatform.model.Product;
import com.example.ecommerceplatform.service.ICategoryService;
import com.example.ecommerceplatform.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class RestProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    // Get list product
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Add Product
    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);

        // Set category for product
        if (productDto.getCategoryId() != null) {
            Category category = categoryService.findById(productDto.getCategoryId());
            product.setCategory(category);
        }

        Product newProduct = productService.save(product);

        // lấy lại sp mới thêm trả ra response
        Product product1 = productService.findById(newProduct.getId());
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }

    // Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Update Product
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BeanUtils.copyProperties(productDto, product);
        // Set category for product
        if (productDto.getCategoryId() != null) {
            Category category = categoryService.findById(productDto.getCategoryId());
            product.setCategory(category);
        }
        Product updatedProduct = productService.save(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Delete Product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
