package com.forohub.forohub.service;

import com.forohub.forohub.model.User;
import com.forohub.forohub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario.
     * @return Usuario encontrado o vacío si no existe.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Crea y guarda un nuevo usuario en la base de datos.
     * @param user Usuario a guardar.
     * @return Usuario guardado.
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encripta la contraseña
        return userRepository.save(user);
    }
}
