package com.forohub.forohub.controller;

import com.forohub.forohub.model.User;
import com.forohub.forohub.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Endpoint para crear un nuevo usuario
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Validación básica
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        // Guardar usuario en la base de datos
        user.setEnabled(true); // Activa el usuario por defecto
        userRepository.save(user);
        return ResponseEntity.ok("Usuario creado con éxito");
    }
}
