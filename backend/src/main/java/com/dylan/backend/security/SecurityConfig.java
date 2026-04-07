package com.dylan.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Marks this as a configuration class that Spring reads on startup
@Configuration
// Enables Spring Security for the whole application
@EnableWebSecurity
public class SecurityConfig {

    // Inject our custom JWT filter
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF - not needed for REST APIs since we use JWT instead of cookies
            .csrf(csrf -> csrf.disable())

            // Configure which endpoints are public and which need authentication
            .authorizeHttpRequests(auth -> auth
                // Allow register and login without a token
                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                // Every other endpoint requires a valid JWT token
                .anyRequest().authenticated()
            )

            // Tell Spring not to create sessions - we use JWT so we dont need them
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Add our JwtFilter before Spring's built in authentication filter
            // This ensures the token is validated before anything else happens
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // BCrypt bean used throughout the app for hashing and checking passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}