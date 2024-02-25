package com.shah.springjwttutorials.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shah.springjwttutorials.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author NORUL
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleName roleName;
}