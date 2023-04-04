package com.aut.prueba.service;

import com.aut.prueba.model.User;
import com.aut.prueba.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getByUsuario(String nombreUsuario){
        return userRepository.findByUsername(nombreUsuario);
    }

    public Boolean existsByUsuario(String nombreUsuario){
        return userRepository.existsByUsername(nombreUsuario);
    }

    public Boolean existsByCorreo(String email){
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
