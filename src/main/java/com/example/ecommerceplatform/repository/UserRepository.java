package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}