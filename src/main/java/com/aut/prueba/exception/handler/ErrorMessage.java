package com.aut.prueba.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private int statusCode;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    private String message;

    private String contextPath;

}
