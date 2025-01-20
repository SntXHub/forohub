//Proyecto ForoHub Alura ONE
package com.forohub.forohub.controller;

import com.forohub.forohub.model.User;
import com.forohub.forohub.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Declara el PasswordEncoder

    // Inyecta el PasswordEncoder en el constructor
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Crear un nuevo usuario.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        // Validar si el usuario ya existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null); // Retorna 409 si el usuario ya existe
        }

        // Encripta la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Guarda el usuario en la base de datos
        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Obtener todos los usuarios.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Obtener un usuario por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar un usuario.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // Encripta la contraseña
                    user.setPasswordUpdatedAt(LocalDateTime.now()); // Registra la fecha de actualización
                    user.setEnabled(userDetails.getEnabled());
                    user.setRole(userDetails.getRole());
                    userRepository.save(user);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un usuario.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

