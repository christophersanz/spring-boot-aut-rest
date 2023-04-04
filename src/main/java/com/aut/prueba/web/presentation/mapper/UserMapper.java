package com.aut.prueba.web.presentation.mapper;

import com.aut.prueba.model.User;
import com.aut.prueba.web.presentation.UserRequestDto;
import com.aut.prueba.web.presentation.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convertToUser(UserRequestDto userRequestDto);

    UserResponseDto convertToUserResponseDto(User user);

}
