package com.shah.springjwttutorials.repository;

import com.shah.springjwttutorials.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserRegistration, Integer> {
    UserRegistration findByEmail(String email);
}