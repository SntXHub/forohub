package com.forohub.forohub.config;

import com.forohub.forohub.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Habilitar @PreAuthorize y @PostAuthorize
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll() // Login público
                        .requestMatchers("/error").permitAll() // Permitir acceso a /error
                        .requestMatchers("/api/users/**").hasRole("ADMIN") // Solo ADMIN
                        .requestMatchers("/api/topics/**").authenticated() // Usuarios autenticados
                        .requestMatchers(HttpMethod.GET, "/api/topics/**").authenticated() // Ver tópicos: todos autenticados
                        .requestMatchers(HttpMethod.POST, "/api/topics/**").hasAnyRole("USER", "ADMIN") // Crear tópicos
                        .requestMatchers(HttpMethod.PUT, "/api/topics/**").hasRole("ADMIN") // Actualizar: Solo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/topics/**").hasRole("ADMIN") // Eliminar: Solo ADMIN
                        .anyRequest().authenticated() // Todo lo demás requiere autenticación
                )
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
