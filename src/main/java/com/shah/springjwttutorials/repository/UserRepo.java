package com.shah.springjwttutorials.repository;

import com.shah.springjwttutorials.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserRegistration, Integer> {
   Optional<UserRegistration> findByEmail(String email);
}