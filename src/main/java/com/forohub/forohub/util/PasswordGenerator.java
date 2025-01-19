package com.forohub.forohub.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123"; // Contraseña original
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Hash generado: " + encodedPassword);
    }
}