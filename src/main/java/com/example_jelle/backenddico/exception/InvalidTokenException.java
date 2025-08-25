package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when a provided token (e.g., for verification or password reset)
 * is invalid, expired, or does not exist.
 * The @ResponseStatus annotation ensures that Spring automatically returns
 * an HTTP 400 BAD REQUEST status when this exception is handled.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTokenException extends RuntimeException {
    /**
     * Constructs a new InvalidTokenException with the specified detail message.
     * @param message The detail message.
     */
    public InvalidTokenException(String message) {
        super(message);
    }
}
