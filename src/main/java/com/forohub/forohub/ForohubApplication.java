package com.forohub.forohub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.forohub.forohub")
public class ForohubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForohubApplication.class, args);
	}
}
