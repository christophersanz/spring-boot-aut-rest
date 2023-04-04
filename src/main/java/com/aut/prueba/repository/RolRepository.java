package com.aut.prueba.repository;

import com.aut.prueba.model.Rol;
import com.aut.prueba.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}

