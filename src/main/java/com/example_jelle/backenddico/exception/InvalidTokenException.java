// Exception thrown when a provided token is invalid, expired, or does not exist.
package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTokenException extends RuntimeException {
    // Constructs a new InvalidTokenException with the specified detail message.
    public InvalidTokenException(String message) {
        super(message);
    }
}
