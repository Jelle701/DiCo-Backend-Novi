// Exception thrown when an attempt to use an access code fails.
package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAccessException extends RuntimeException {
    // Constructs a new InvalidAccessException with the specified detail message.
    public InvalidAccessException(String message) {
        super(message);
    }
}
