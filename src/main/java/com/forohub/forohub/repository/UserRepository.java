//Proyecto ForoHub Alura ONE
package com.forohub.forohub.repository;

import com.forohub.forohub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

