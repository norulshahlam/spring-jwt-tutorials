package com.shah.springjwttutorials.service;

import com.shah.springjwttutorials.dto.AuthenticationRequest;
import com.shah.springjwttutorials.dto.AuthenticationResponse;
import com.shah.springjwttutorials.entity.Role;
import com.shah.springjwttutorials.entity.UserRegistration;
import com.shah.springjwttutorials.jwt.JwtService;
import com.shah.springjwttutorials.repository.RoleRepo;
import com.shah.springjwttutorials.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author NORUL
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserRegistration user = userRepo
                .findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
    public UserRegistration saveUser(UserRegistration user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getRoleName());
        return roleRepo.save(role);
    }

}
