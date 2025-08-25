package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when an attempt is made to register a new user
 * with an email address that is already present in the database.
 * The @ResponseStatus annotation ensures that Spring automatically returns
 * an HTTP 409 CONFLICT status when this exception is handled.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExists extends RuntimeException {
    /**
     * Constructs a new EmailAlreadyExists exception with the specified detail message.
     * @param message The detail message.
     */
    public EmailAlreadyExists(String message) {
        super(message);
    }
}
