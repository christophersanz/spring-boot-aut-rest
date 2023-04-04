package com.aut.prueba.web.controller;

import com.aut.prueba.exception.Message;
import com.aut.prueba.exception.handler.BadRequestException;
import com.aut.prueba.model.Rol;
import com.aut.prueba.model.User;
import com.aut.prueba.security.AuthenticationRequest;
import com.aut.prueba.security.AuthenticationResponse;
import com.aut.prueba.security.JwtService;
import com.aut.prueba.service.MyUserDetailService;
import com.aut.prueba.service.RolService;
import com.aut.prueba.service.UserService;
import com.aut.prueba.web.presentation.UserRequestDto;
import com.aut.prueba.web.presentation.UserResponseDto;
import com.aut.prueba.web.presentation.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.aut.prueba.util.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailService myUserDetailService;
    private final JwtService jwtService;

    private final UserService userService;
    private final RolService rolService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException ("Invalid username or password", e);
        }
        UserDetails userDetails = myUserDetailService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtService.createToken(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestDto) throws Exception {

        //get roles
        Set<Rol> roles = new HashSet<>();
        userRequestDto.getRoles().forEach(name -> roles.add(rolService.getByRolNombre(name.getRolNombre()).get()));
        userRequestDto.setRoles(roles);

        //convert DTO to entity
        User user = userMapper.convertToUser(userRequestDto);

        //format validations
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());

        //encript password
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        //validate user
        if(userService.existsByUsuario(user.getUsername())){
            return new ResponseEntity<>(new Message("El nombre de usario "+userRequestDto.getUsername()+" ya existe"), HttpStatus.BAD_REQUEST);
        }

        //validate email
        if(userService.existsByCorreo(user.getEmail())){
            return new ResponseEntity<>(new Message("El email "+userRequestDto.getEmail()+" ya existe"), HttpStatus.BAD_REQUEST);
        }

        try {
            user = userService.save(user);
        } catch (Exception e) {
            throw new BadRequestException("Error al crear el usuario");
        }

        //convert entity to DTO
        return new ResponseEntity<>(userMapper.convertToUserResponseDto(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/test")
    public String test() {
        return "test";
    }
}
