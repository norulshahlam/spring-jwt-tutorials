package com.shah.springjwttutorials.controller;

import com.shah.springjwttutorials.dto.AuthenticationRequest;
import com.shah.springjwttutorials.dto.AuthenticationResponse;
import com.shah.springjwttutorials.service.AuthService;
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

    private final AuthService service;

    public UserController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        log.info("Logging in...");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
