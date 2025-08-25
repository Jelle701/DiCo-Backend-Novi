package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when an attempt to use an access code fails.
 * This can happen if the code is incorrect, has expired, or is otherwise invalid.
 * The @ResponseStatus annotation ensures that Spring automatically returns
 * an HTTP 400 BAD REQUEST status when this exception is handled.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAccessException extends RuntimeException {
    /**
     * Constructs a new InvalidAccessException with the specified detail message.
     * @param message The detail message explaining the reason for the invalid access.
     */
    public InvalidAccessException(String message) {
        super(message);
    }
}
