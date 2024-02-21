package com.shah.springjwttutorials.repository;


import com.shah.springjwttutorials.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByRoleName(String name);
}