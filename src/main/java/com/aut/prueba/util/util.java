package com.aut.prueba.util;

import com.aut.prueba.exception.handler.BadRequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class util {

    private static final String regex_addresses = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String regex_password = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

    public static void validateEmail(String valor) {
        Pattern pattern = Pattern.compile(regex_addresses);
        Matcher matcher = pattern.matcher(valor);
        if (!matcher.matches()) {
            throw new BadRequestException("Error en el formato del correo");
        }
    }

    public static void validatePassword(String valor) {
        Pattern pattern = Pattern.compile(regex_password);
        Matcher matcher = pattern.matcher(valor);
        if (!matcher.matches()) {
            throw new BadRequestException("Error en el formato del password.");
        }
    }


}
