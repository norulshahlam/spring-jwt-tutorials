package com.shah.springjwttutorials.controller;

import com.shah.springjwttutorials.dto.MyResponse;
import com.shah.springjwttutorials.dto.UserLoginRequest;
import com.shah.springjwttutorials.dto.UserLoginResponse;
import com.shah.springjwttutorials.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class UserController {

    private final UserServiceImpl service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<MyResponse<UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest request) {
        log.info("Logging in...");
        return ResponseEntity.ok(service.login(request));
    }
}
