package com.shah.springjwttutorials.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NORUL
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistration implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email
    private String email;
    private String name;
    private String password;
    /**
     * This is needed as one user can have the same role as other user and other role
     * e.g. admin user can have role ADMIN and APPROVER while approver user can have only APPROVER role
     */
    @ManyToMany
    private List<Role> roles = new ArrayList<>();
}