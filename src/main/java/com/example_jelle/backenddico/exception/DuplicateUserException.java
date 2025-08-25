package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when an attempt is made to create a user that already exists,
 * for example, with a duplicate username or email.
 * The @ResponseStatus annotation ensures that Spring automatically returns
 * an HTTP 409 CONFLICT status when this exception is handled.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUserException extends RuntimeException {
    /**
     * Constructs a new DuplicateUserException with the specified detail message.
     * @param message The detail message.
     */
    public DuplicateUserException(String message) {
        super(message);
    }
}
