package com.shah.springjwttutorials.controller;

import com.shah.springjwttutorials.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NORUL
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class MyController {

    private final UserService userService;

    public MyController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/admin")
    @Operation(summary = "for user having ADMIN role",
            description = "This endpoint will be accessible for user having ADMIN role")
    public ResponseEntity<String> admin() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello Admin");
    }

    @GetMapping("/assessor")
    @Operation(summary = "for user having ASSESSOR role",
            description = "This endpoint will be accessible for user having ASSESSOR role")
    public ResponseEntity<String> assessor() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello Assessor");
    }

    @GetMapping("/applicant")
    @Operation(summary = "for user having APPLICANT role",
            description = "This endpoint will be accessible for user having APPLICANT role")
    public ResponseEntity<String> applicant() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello applicant");
    }

    @GetMapping("/approver")
    @Operation(summary = "for user having APPROVER role",
            description = "This endpoint will be accessible for user having APPROVER role")
    public ResponseEntity<String> approver() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello approver");
    }
 @GetMapping("/anyRole")
    @Operation(summary = "for user having any role",
            description = "This endpoint will be accessible for user having any role. No need for credentials. You " +
                    "can use current credentials but wrong one will trigger 401")
    public ResponseEntity<String> anyRole() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello any role");
    }


}
