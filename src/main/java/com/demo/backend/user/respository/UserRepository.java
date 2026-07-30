package com.demo.backend.user.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.backend.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    User findByPhone(String phone);
}
