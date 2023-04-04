package com.aut.prueba.service;

import com.aut.prueba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //return new MyUserDetails(s);

        User usuario = userService.getByUsuario(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario o password incorrectos");
        }

        List<GrantedAuthority> authorities =
                usuario.getRoles()
                        .stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name()))
                        .collect(Collectors.toList());
        if(authorities.isEmpty()) {
            throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
        }

        return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }
}
