package com.shah.springjwttutorials.repository;


import com.shah.springjwttutorials.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Role findByRoleName(String name);
}