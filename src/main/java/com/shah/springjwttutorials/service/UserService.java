package com.shah.springjwttutorials.service;

import com.shah.springjwttutorials.dto.AuthenticationRequest;
import com.shah.springjwttutorials.dto.AuthenticationResponse;
import com.shah.springjwttutorials.entity.Role;
import com.shah.springjwttutorials.entity.UserRegistration;

/**
 * @author NORUL
 */
public interface UserService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
    Role saveRole(Role role);
    UserRegistration saveUser(UserRegistration user);
}
