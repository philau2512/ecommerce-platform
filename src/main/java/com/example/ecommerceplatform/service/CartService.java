package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.CartItem;
import com.example.ecommerceplatform.model.Product;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.repository.ICartItemRepository;
import com.example.ecommerceplatform.repository.IProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService implements ICartService {

    private final ICartItemRepository cartItemRepository;
    private final IProductRepository productRepository;

    public CartService(ICartItemRepository cartItemRepository, IProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addProductToCart(User user, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        cartItemRepository.save(cartItem);
    }
}

