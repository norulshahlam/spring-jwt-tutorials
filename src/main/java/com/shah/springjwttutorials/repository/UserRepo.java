package com.shah.springjwttutorials.repository;

import com.shah.springjwttutorials.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<UserRegistration, UUID> {
    UserRegistration findByEmail(String email);
}