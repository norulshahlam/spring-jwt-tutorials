package com.shah.springjwttutorials.service;

import com.shah.springjwttutorials.dto.MyResponse;
import com.shah.springjwttutorials.dto.UserLoginRequest;
import com.shah.springjwttutorials.dto.UserLoginResponse;
import com.shah.springjwttutorials.entity.Role;
import com.shah.springjwttutorials.entity.UserRegistration;
import com.shah.springjwttutorials.jwt.JwtService;
import com.shah.springjwttutorials.repository.RoleRepo;
import com.shah.springjwttutorials.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.shah.springjwttutorials.constants.MyConstants.ACCESS_TOKEN;
import static com.shah.springjwttutorials.constants.MyConstants.REFRESH_TOKEN;
import static com.shah.springjwttutorials.dto.MyResponse.successResponse;

/**
 * @author NORUL
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public MyResponse<UserLoginResponse> login(UserLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));
        UserRegistration user = userRepo.findByEmail(request.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("Email [" + request.getEmail() + "] not found"));
        String jwtAccessToken = jwtService.generateToken(user, ACCESS_TOKEN);
        String jwtRefreshToken = jwtService.generateToken(user, REFRESH_TOKEN);
        UserLoginResponse response = UserLoginResponse.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
        return successResponse(response);
    }

    public MyResponse<UserRegistration> registerUser(UserRegistration user) {

        // Check if email is already registered
        userRepo.findByEmail(user.getEmail()).ifPresent(i -> {
            throw new UsernameNotFoundException("Email [" + i.getEmail() + "] exists");
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roles2 = new ArrayList<>();

        // Handle NPE if user provides null values for roles
        if (ObjectUtils.isNotEmpty(user.getRoles())) {

            // Filter duplication of roles & throw if roles doesn't exist
            new HashSet<>(user.getRoles()).forEach(role -> roleRepo.findByRoleName(role.getRoleName())
                    .ifPresentOrElse(roles2::add, () -> {
                        throw new UsernameNotFoundException("Role [" + role + "] not found");
                    }));
        }
        // Update user roles with existing roles
        user.setRoles(roles2);

        log.info("Saving new user {} to the database", user);
        return successResponse(userRepo.save(user));
    }

    public Role addRole(Role role) {
        log.info("Adding role {} to the database", role.getRoleName());
        return roleRepo.save(role);
    }

}
