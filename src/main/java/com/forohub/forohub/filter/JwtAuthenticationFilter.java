package com.forohub.forohub.filter;

import com.forohub.forohub.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("Ejecutando JwtAuthenticationFilter para la solicitud: " + request.getRequestURI());

        String token = getTokenFromHeader(request);

        if (token != null) {
            String username = tokenService.validateToken(token);

            if (username != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.emptyList() // Puedes cargar roles si es necesario
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Depuración: Verificar autenticación
                System.out.println("Usuario autenticado: " + SecurityContextHolder.getContext().getAuthentication());
            } else {
                System.out.println("El token no es válido.");
            }
        } else {
            System.out.println("No se encontró token en el encabezado.");
        }

        chain.doFilter(request, response);
    }

    /**
     * Indica si el filtro debe ignorar ciertas rutas (Swagger en este caso).
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Ignorar rutas relacionadas con Swagger
        return path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs");
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
