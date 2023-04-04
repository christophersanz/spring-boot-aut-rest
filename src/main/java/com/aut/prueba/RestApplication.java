package com.aut.prueba;

import com.aut.prueba.model.Rol;
import com.aut.prueba.model.User;
import com.aut.prueba.security.enums.RolNombre;
import com.aut.prueba.service.RolService;
import com.aut.prueba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@SpringBootApplication
public class RestApplication implements CommandLineRunner {

    @Autowired
    private RolService rolService;

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        rolService.save(rolAdmin);
        rolService.save(rolUser);

        User user1 = User.builder()
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("pass"))
                .email("admin@admin.com")
                .roles(new HashSet<>(List.of(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get(), rolService.getByRolNombre(RolNombre.ROLE_USER).get())))
                .enabled(Boolean.TRUE)
                .build();
        userService.save(user1);

        User user2 = User.builder()
                .username("user")
                .password(new BCryptPasswordEncoder().encode("pass"))
                .email("user@user.com")
                .roles(new HashSet<>(List.of(rolService.getByRolNombre(RolNombre.ROLE_USER).get())))
                .enabled(Boolean.TRUE)
                .build();
        userService.save(user2);

    }
}
