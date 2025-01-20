package com.forohub.forohub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ForohubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForohubApplication.class, args);
	}

	@Bean
	public CommandLineRunner generatePassword(PasswordEncoder passwordEncoder) {
		return args -> {
			String rawPassword = "12345"; // Contraseña que deseas usar
			String encodedPassword = passwordEncoder.encode(rawPassword);
			System.out.println("Contraseña encriptada: " + encodedPassword);
		};
	}
}
