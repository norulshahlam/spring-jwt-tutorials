package com.shah.springjwttutorials.service;

import com.shah.springjwttutorials.dto.UserLoginRequest;
import com.shah.springjwttutorials.dto.UserLoginResponse;
import com.shah.springjwttutorials.entity.Role;
import com.shah.springjwttutorials.entity.UserRegistration;

/**
 * @author NORUL
 */
public interface UserService {

    UserLoginResponse login(UserLoginRequest request);
    Role saveRole(Role role);
    UserRegistration saveUser(UserRegistration user);
}
