package com.shah.springjwttutorials.service;

import com.shah.springjwttutorials.dto.UserLoginResponse;
import com.shah.springjwttutorials.dto.UserLoginRequest;
import com.shah.springjwttutorials.entity.Role;
import com.shah.springjwttutorials.entity.UserRegistration;
import com.shah.springjwttutorials.jwt.JwtService;
import com.shah.springjwttutorials.repository.RoleRepo;
import com.shah.springjwttutorials.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.shah.springjwttutorials.constants.MyConstants.ACCESS_TOKEN;
import static com.shah.springjwttutorials.constants.MyConstants.REFRESH_TOKEN;

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

    public UserLoginResponse login(UserLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));
        UserRegistration user = userRepo.findByEmail(request.getEmail()).orElseThrow(() ->
                        new UsernameNotFoundException("Email [" + request.getEmail() + "] not found"));
        String jwtAccessToken = jwtService.generateToken(user, ACCESS_TOKEN);
        String jwtRefreshToken = jwtService.generateToken(user, REFRESH_TOKEN);
        return UserLoginResponse.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

    public UserRegistration registerUser(UserRegistration user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Role addRole(Role role) {
        log.info("Adding role {} to the database", role.getRoleName());
        return roleRepo.save(role);
    }

}
