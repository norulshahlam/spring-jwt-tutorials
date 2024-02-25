package com.shah.springjwttutorials.service;

import com.shah.springjwttutorials.dto.MyResponse;
import com.shah.springjwttutorials.dto.UserLoginRequest;
import com.shah.springjwttutorials.entity.Role;
import com.shah.springjwttutorials.entity.UserRegistration;

/**
 * @author NORUL
 */
public interface UserService {

    MyResponse login(UserLoginRequest request);
    Role addRole(Role role);
    MyResponse registerUser(UserRegistration user);
}
