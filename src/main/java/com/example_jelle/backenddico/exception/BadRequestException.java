package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown to indicate that a client's request is malformed or invalid.
 * The @ResponseStatus annotation ensures that Spring automatically returns
 * an HTTP 400 BAD REQUEST status when this exception is handled.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    /**
     * Constructs a new BadRequestException with the specified detail message.
     * @param message The detail message.
     */
    public BadRequestException(String message) {
        super(message);
    }
}
