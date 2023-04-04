package com.aut.prueba.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERAL_ERROR_MESSAGE = "error general";
    public static final String VALIDATON_ERROR_MESSAGE = "error de validación";

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage validateHandler(BadRequestException ex, HttpServletRequest request) {
        log.error("An error happened while calling API: {}", ex.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("An error happened while calling API: {}", ex.getMessage());
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(RuntimeException ex, HttpStatus status, WebRequest request) {
        log.error("An error happened while calling API: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage(status.value(), new Date(), VALIDATON_ERROR_MESSAGE, request.getContextPath()), status);
    }

}
