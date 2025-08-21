package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for handling invalid access code attempts.
 * The @ResponseStatus annotation tells Spring to return a 400 Bad Request status.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAccessException extends RuntimeException {
    public InvalidAccessException(String message) {
        super(message);
    }
}
