package com.shah.springjwttutorials.config;

import com.shah.springjwttutorials.service.JwtAuthenticationFilter;
import com.shah.springjwttutorials.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.shah.springjwttutorials.enums.RoleName.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author NORUL
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        // Using Spring to encode password for you - Not secure
        UserDetails applicant = User
                .withUsername("applicant")
                .password((passwordEncoder().encode("password")))
                .roles(APPLICANT.name())
                .build();
        UserDetails assessor = User
                .withUsername("assessor")
                .password((passwordEncoder().encode("password")))
                .roles(ASSESSOR.name())
                .build();
        UserDetails approver = User
                .withUsername("approver")
                .password((passwordEncoder().encode("password")))
                .roles(APPROVER.name())
                .build();

        // You encode yourself then insert - value is `password`
        UserDetails admin = User
                .withUsername("admin")
                .password("$2a$09$9j2c1BTj4zMU.oaaSNumhOfgYuK21hfNiDR.H8HoE677Vh3kPsuQC")
                .roles(ADMIN.name())
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
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html",
                                        "/api/v1/anyRole",
                                        "/api/v1/login")
                                .permitAll()
                                .requestMatchers(GET, "/api/v1/admin")
                                .hasAnyRole(ADMIN.name())
                                .requestMatchers(GET, "/api/v1/applicant")
                                .hasAnyRole(APPLICANT.name())
                                .requestMatchers(GET, "/api/v1/assessor")
                                .hasAnyRole(ASSESSOR.name())
                                .requestMatchers(GET, "/api/v1/approver")
                                .hasAnyRole(APPROVER.name())
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


}
