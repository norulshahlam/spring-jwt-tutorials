package com.shah.springjwttutorials.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.shah.springjwttutorials.enums.RoleName.*;
import static org.springframework.http.HttpMethod.GET;

/**
 * @author NORUL
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        // Using Spring to encode password for you - Not secure
        UserDetails applicant = User
                .withUsername("applicant")
                .password((passwordEncoder().encode("password")))
                .roles(APPLICANT.name())
                .authorities(APPLICANT.name())
                .build();
        UserDetails assessor = User
                .withUsername("assessor")
                .password((passwordEncoder().encode("password")))
                .roles(ASSESSOR.name())
                .authorities(ASSESSOR.name())
                .build();
        UserDetails approver = User
                .withUsername("approver")
                .password((passwordEncoder().encode("password")))
                .roles(APPROVER.name())
                .authorities(APPROVER.name())
                .build();

        // You encode yourself then insert - value is `password`
        UserDetails admin = User
                .withUsername("admin")
                .password("$2a$09$9j2c1BTj4zMU.oaaSNumhOfgYuK21hfNiDR.H8HoE677Vh3kPsuQC")
                .roles(ADMIN.name())
                .authorities(ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(admin, applicant, assessor, approver);
    }

    /**
     * Allow endpoint with specific roles
     * Allow h2 console
     * Secure all other endpoints, including Swagger
     * <a href="https://www.youtube.com/watch?v=awcCiqBO36E&list=PLZV0a2jwt22s5NCKOwSmHVagoDW8nflaC&index=7">...</a>
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/h2-console/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html")
                                .permitAll()
                                .requestMatchers(GET, "/api/v1/admin")
                                .hasAuthority(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/applicant")
                                .hasAuthority(APPLICANT.name())
                                .requestMatchers(GET, "/api/v1/assessor")
                                .hasAuthority(ASSESSOR.name())
                                .requestMatchers(GET, "/api/v1/approver")
                                .hasAuthority(APPROVER.name())
                                .anyRequest().authenticated())
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
