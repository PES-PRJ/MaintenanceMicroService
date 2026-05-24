package bt.gcit.edu.maintenance.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // DEBUG LOG 1
        System.out.println("--- Incoming Request URI: " + request.getRequestURI());
        System.out.println("--- Authorization Header Present: " + (authHeader != null));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("--- Request skipped JWT filter because Bearer header is missing or incorrect.");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            userEmail = jwtUtils.extractUsername(jwt);
            System.out.println("--- Extracted User Email from Token: " + userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtils.isTokenValid(jwt)) {
                    String role = jwtUtils.extractRole(jwt);
                    System.out.println("--- Token is Valid! Extracted Role Claim: " + role);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userEmail, null, Collections.singletonList(new SimpleGrantedAuthority(role)));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("--- Successfully populated Spring Security Context Authorities: "
                            + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                } else {
                    System.out.println(
                            "--- JWT Token parsing succeeded but validation failed (Expired or broken time checks).");
                }
            }
        } catch (Exception e) {
            System.out.println("--- CRITICAL ERROR inside JwtFilter processing: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}