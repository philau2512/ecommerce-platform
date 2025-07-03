package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Role;
import com.example.ecommerceplatform.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAllCustomers();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Role findRoleByName(String roleName);
}
