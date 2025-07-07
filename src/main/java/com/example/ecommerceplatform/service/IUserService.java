package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    void deleteUser(Long userId);
}