package com.shah.springjwttutorials.startup;

import com.shah.springjwttutorials.entity.Role;
import com.shah.springjwttutorials.entity.UserRegistration;
import com.shah.springjwttutorials.repository.RoleRepo;
import com.shah.springjwttutorials.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.shah.springjwttutorials.enums.RoleName.*;

@Component
@Slf4j
public class MyStartup {

    @Bean
    CommandLineRunner run(UserRepo userRepo, RoleRepo roleRepo) {
        return args -> {

            log.info("Deleting existing data...");
            userRepo.deleteAll();
            roleRepo.deleteAll();
            Role admin = Role.builder().roleName(ADMIN).build();
            Role applicant = Role.builder().roleName(APPLICANT).build();
            Role assessor = Role.builder().roleName(ASSESSOR).build();
            Role approver = Role.builder().roleName(APPROVER).build();

            log.info("Saving roles...");
            roleRepo.saveAll(List.of(admin, applicant, assessor, approver));

            UserRegistration userAdmin = UserRegistration.builder()
                    .email("admin@gmail.com")
                    .name("admin")
                    .password("1234")
                    .roles(List.of(admin, approver))
                    .build();
            UserRegistration userApplicant = UserRegistration.builder()
                    .email("applicant@gmail.com")
                    .name("applicant")
                    .password("1234")
                    .roles(List.of(applicant))
                    .build();
            UserRegistration userAssessor = UserRegistration.builder()
                    .email("assessor@gmail.com")
                    .name("assessor")
                    .password("1234")
                    .roles(List.of(assessor))
                    .build();
            UserRegistration userApprover = UserRegistration.builder()
                    .email("approver@gmail.com")
                    .name("approver")
                    .password("1234")
                    .roles(List.of(approver))
                    .build();

            log.info("Saving users...");
            userRepo.saveAll(List.of(userAdmin, userApplicant, userAssessor, userApprover));
            userRepo.save(userAdmin);
            userRepo.save(userApplicant);
            userRepo.save(userAssessor);
            userRepo.save(userApprover);
        };
    }
}
