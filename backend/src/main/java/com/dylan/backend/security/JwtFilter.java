package com.dylan.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;

// OncePerRequestFilter ensures this filter runs exactly once per request
@Component
public class JwtFilter extends OncePerRequestFilter {

    // JwtUtil handles all token operations like validation and extracting username
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Grab the Authorization header from the incoming request
        // It should look like: "Bearer eyJhbGciOiJIUzI1NiJ9..."
        String authHeader = request.getHeader("Authorization");

        // Check if the header exists and starts with "Bearer "
        // If not, we skip token validation and just continue the request
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Remove "Bearer " (7 characters) from the start to get just the token
            String token = authHeader.substring(7);

            // Validate the token - checks if it's properly signed and not expired
            if (jwtUtil.validateToken(token)) {

                // Extract the username that was baked into the token when it was created
                String username = jwtUtil.extractUsername(token);

                // Create a Spring Security authentication object with the username
                // null = no credentials needed since token is already validated
                // new ArrayList<>() = no specific roles/authorities for now
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                // Tell Spring Security who the current logged in user is
                // This is how Spring knows the request is authenticated
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Pass the request along to the next filter or controller
        // This runs regardless of whether the token was valid or not
        // SecurityConfig decides what happens to unauthenticated requests
        filterChain.doFilter(request, response);
    }
}