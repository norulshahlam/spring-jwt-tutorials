package com.shah.springjwttutorials.service;

import com.shah.springjwttutorials.pojo.dto.MyResponse;
import com.shah.springjwttutorials.pojo.dto.UserLoginRequest;
import com.shah.springjwttutorials.pojo.entity.Role;
import com.shah.springjwttutorials.pojo.entity.UserRegistration;

/**
 * @author NORUL
 */
public interface UserService {

    MyResponse login(UserLoginRequest request);
    Role addRole(Role role);
    MyResponse registerUser(UserRegistration user);
}
