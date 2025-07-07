package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllByRoleName(String roleName);
    List<User> findAllByFullNameContainingIgnoreCase(String keyword);
    List<User> findAllByFullNameContainingIgnoreCaseAndRoleName(String keyword, String roleName);
}