package com.shah.springjwttutorials;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author NORUL
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class SpringJwtTutorialsApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringJwtTutorialsApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
