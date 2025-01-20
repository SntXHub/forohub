package com.forohub.forohub.controller;

import com.forohub.forohub.dto.AuthResponse;
import com.forohub.forohub.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid Map<String, String> authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.get("username"),
                        authRequest.get("password")
                )
        );

        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String token = tokenService.generateToken(username, role);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String token) {
        // Eliminar el prefijo "Bearer "
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Validar el token
        String username = tokenService.validateToken(jwt);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Generar un nuevo token con el mismo username y rol
        String role = tokenService.getClaims(jwt).get("role", String.class);
        String newToken = tokenService.generateToken(username, role);

        return ResponseEntity.ok(new AuthResponse(newToken));
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "AuthController está funcionando correctamente.";
    }
}


