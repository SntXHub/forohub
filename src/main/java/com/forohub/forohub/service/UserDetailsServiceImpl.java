package com.forohub.forohub.service;

import com.forohub.forohub.model.User;
import com.forohub.forohub.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario en la base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Construye un objeto UserDetails con los datos del usuario
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername()) // Nombre de usuario
                .password(user.getPassword()) // Contraseña encriptada
                .roles(user.getRole().replace("ROLE_", "")) // Roles (sin el prefijo ROLE_)
                .build();
    }
}

