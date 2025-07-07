package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Category;
import com.example.ecommerceplatform.model.Product;
import com.example.ecommerceplatform.repository.ICategoryRepository;
import com.example.ecommerceplatform.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public Category save(Category category) {
        // Kiểm tra tên danh mục không được trùng lặp (tùy chọn)
        if (category.getId() == null) {
            // Nếu là thêm mới
            // Có thể thêm logic kiểm tra trùng lặp ở đây nếu cần
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Kiểm tra xem có sản phẩm nào thuộc danh mục này không
        List<Product> products = productRepository.findByCategoryId(id);

        if (!products.isEmpty()) {
            // Có 2 cách xử lý:
            // 1. Không cho phép xóa và thông báo lỗi
            throw new RuntimeException("Không thể xóa danh mục này vì có " + products.size() + " sản phẩm thuộc danh mục!");

            // 2. Hoặc chuyển tất cả sản phẩm sang danh mục mặc định và cho phép xóa
            /*
            Category defaultCategory = categoryRepository.findById(1L)  // ID danh mục mặc định
                    .orElseThrow(() -> new RuntimeException("Default category not found"));

            for (Product product : products) {
                product.setCategory(defaultCategory);
                productRepository.save(product);
            }
            categoryRepository.deleteById(id);
            */
        } else {
            // Không có sản phẩm nào thì xóa bình thường
            categoryRepository.deleteById(id);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public List<Category> findByNameContaining(String keyword) {
        return categoryRepository.findByNameContaining(keyword);
    }
}