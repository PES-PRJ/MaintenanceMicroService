package bt.gcit.edu.maintenance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Employees can file tickets and look up their personal history
                        .requestMatchers(HttpMethod.POST, "/api/maintenance/tickets")
                        .hasAnyAuthority("EMPLOYEE", "ASSETMANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/maintenance/my-tickets")
                        .hasAnyAuthority("EMPLOYEE", "ASSETMANAGER")

                        // 2. Only Asset Managers can look at all open tickets and modify their progress
                        // status
                        .requestMatchers(HttpMethod.GET, "/api/maintenance/tickets").hasAuthority("ASSETMANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/maintenance/tickets/{id}/status")
                        .hasAuthority("ASSETMANAGER")
                        // Place this inside authorizeHttpRequests alongside your other matchers
                        .requestMatchers(HttpMethod.DELETE, "/api/maintenance/tickets/{id}")
                        .hasAuthority("ASSETMANAGER")
                        
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}