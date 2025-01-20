package com.forohub.forohub.controller;

import com.forohub.forohub.dto.AuthResponse;
import com.forohub.forohub.service.TokenService;
import jakarta.validation.Valid;
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

    @GetMapping("/test")
    public String testEndpoint() {
        return "AuthController está funcionando correctamente.";
    }
}


