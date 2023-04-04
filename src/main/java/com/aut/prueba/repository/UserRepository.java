package com.aut.prueba.repository;

import com.aut.prueba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String nombreUsuario);

    boolean existsByUsername(String nombreUsuario);

    boolean existsByEmail(String email);

}
