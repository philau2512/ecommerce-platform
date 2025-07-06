package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.model.Role;
import com.example.ecommerceplatform.model.User;
import com.example.ecommerceplatform.repository.IRoleRepository;
import com.example.ecommerceplatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<User> findAllCustomers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();  // Đúng: trả về true/false
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }

    @Override
    public List<User> findAllByRoleName(String roleName) {
        return userRepository.findAllByRoleName(roleName);
    }

    @Override
    public List<User> findAllByFullNameContainingIgnoreCase(String keyword) {
        return userRepository.findAllByFullNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<User> findAllByFullNameContainingIgnoreCaseAndRoleName(String keyword, String roleName) {
        return userRepository.findAllByFullNameContainingIgnoreCaseAndRoleName(keyword, roleName);
    }
}
