package com.forohub.forohub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/topics/**").authenticated() // Requiere autenticación para acceder a las rutas de tópicos
                        .requestMatchers("/api/topics/{id}").permitAll() // Permitir acceso público para obtener un tópico específico
                        .requestMatchers("/api/topics/**", "POST", "PUT").hasAnyRole("USER", "ADMIN") // CRUD permitido para usuarios autenticados
                        .requestMatchers("/api/topics/**", "DELETE").hasRole("ADMIN") // Solo ADMIN puede eliminar tópicos
                        .anyRequest().permitAll() // Permitir acceso público para cualquier otra solicitud
                )
                .httpBasic(httpBasic -> {}); // Usar autenticación básica sin configuración adicional

        return http.build();
    }

    // Bean para el cifrado de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}