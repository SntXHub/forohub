//Proyecto ForoHub Alura ONE
package com.forohub.forohub.service;

import com.forohub.forohub.model.User;
import com.forohub.forohub.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Verificar si la contraseña ha expirado
        if (user.getPasswordUpdatedAt() != null) {
            long daysSinceUpdate = Duration.between(
                    user.getPasswordUpdatedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    Instant.now()
            ).toDays();
            if (daysSinceUpdate > 90) {
                throw new RuntimeException("Contraseña expirada. Por favor, actualízala.");
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true, // Cuenta no expirada
                true, // Credenciales no expiradas
                true, // Cuenta no bloqueada
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
