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
public class TestController {

    private final UserService userService;

    public TestController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/admin")
    @Operation(summary = "for user having ADMIN role",
            description = "This endpoint will be accessible for user having ADMIN role")
    public ResponseEntity<String> admin() {
        log.info("In TestController:admin");
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello Admin");
    }

    @GetMapping("/assessor")
    @Operation(summary = "for user having ASSESSOR role",
            description = "This endpoint will be accessible for user having ASSESSOR role")
    public ResponseEntity<String> assessor() {
        log.info("In TestController:assessor");
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello Assessor");
    }

    @GetMapping("/applicant")
    @Operation(summary = "for user having APPLICANT role",
            description = "This endpoint will be accessible for user having APPLICANT role")
    public ResponseEntity<String> applicant() {
        log.info("In TestController:applicant");
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello applicant");
    }

    @GetMapping("/approver")
    @Operation(summary = "for user having APPROVER role",
            description = "This endpoint will be accessible for user having APPROVER role")
    public ResponseEntity<String> approver() {
        log.info("In TestController:approver");
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello approver");
    }
 @GetMapping("/anyRole")
    @Operation(summary = "for user having any role",
            description = "This endpoint will be accessible for user having any role. No need for credentials. You " +
                    "can use current credentials but wrong one will trigger 401")
    public ResponseEntity<String> anyRole() {
     log.info("In TestController:anyRole");
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello any role");
    }


}
