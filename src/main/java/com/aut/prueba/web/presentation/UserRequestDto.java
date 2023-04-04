package com.aut.prueba.web.presentation;

import com.aut.prueba.model.Phone;
import com.aut.prueba.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String username;

    private String password;

    private String email;

    private Boolean enabled;

    private List<Phone> phones;

    private Set<Rol> roles;
}
