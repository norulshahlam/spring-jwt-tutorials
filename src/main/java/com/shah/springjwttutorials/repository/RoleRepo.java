package com.shah.springjwttutorials.repository;


import com.shah.springjwttutorials.pojo.entity.Role;
import com.shah.springjwttutorials.pojo.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleName name);
}