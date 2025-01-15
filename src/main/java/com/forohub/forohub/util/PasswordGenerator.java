package com.forohub.forohub.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

/**
 * Clase para generar contraseñas encriptadas utilizando BCrypt.
 * Puede utilizarse para configurar contraseñas seguras en la base de datos.
 */
public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Leer la contraseña desde la consola
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa la contraseña a encriptar: ");
        String rawPassword = scanner.nextLine();

        // Generar la contraseña encriptada
        String encodedPassword = encoder.encode(rawPassword);

        // Mostrar resultados
        System.out.println("Contraseña original: " + rawPassword);
        System.out.println("Contraseña encriptada: " + encodedPassword);
    }
}
