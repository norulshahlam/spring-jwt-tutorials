package com.shah.springjwttutorials.controller;

import com.shah.springjwttutorials.service.UserService;
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
    public ResponseEntity<String> admin() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello Admin");
    }

    @GetMapping("/assessor")
    public ResponseEntity<String> assessor() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello Assessor");
    }

    @GetMapping("/applicant")
    public ResponseEntity<String> applicant() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello applicant");
    }

    @GetMapping("/approver")
    public ResponseEntity<String> approver() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Hello approver");
    }


}
