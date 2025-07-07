package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.dto.CategoryDto;
import com.example.ecommerceplatform.model.Category;
import com.example.ecommerceplatform.service.ICategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class RestCategoryController {
    @Autowired
    private ICategoryService categoryService;

    // Get list category
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 (No Content)
        }
        return new ResponseEntity<>(categories, HttpStatus.OK); // 200 (OK)
    }

    // Add Category
    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        Category newCategory = categoryService.save(category);
        Category category1 = categoryService.findById(newCategory.getId());
        return new ResponseEntity<>(category1, HttpStatus.CREATED); // 201 (Created)
    }

    // Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 (Not Found)
        }
        return new ResponseEntity<>(category, HttpStatus.OK); // 200 (OK)
    }

    // Update category
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 (Not Found)
        }
        BeanUtils.copyProperties(categoryDto, category);
        Category updatedCategory = categoryService.save(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK); // 200 (OK)
    }

    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 (Not Found)
        }
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 (No Content)
    }
}
