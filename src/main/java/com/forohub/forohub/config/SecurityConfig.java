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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Habilitar seguridad basada en anotaciones @PreAuthorize y similares
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura las reglas de autorización y el filtro de JWT.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF porque usamos tokens JWT
                .authorizeHttpRequests(auth -> auth
                        // Acceso público a endpoints específicos
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Acceso a recursos de tópicos basado en roles y autenticación
                        .requestMatchers(HttpMethod.GET, "/api/topics/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/topics/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/topics/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/topics/**").hasRole("ADMIN")
                        // Todas las demás solicitudes requieren autenticación
                        .anyRequest().authenticated()
                )
                // Agrega el filtro JWT antes del filtro de autenticación estándar
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Proveedor de codificación de contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el AuthenticationManager para gestionar la autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}



