package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when an operation attempts to retrieve a user
 * that does not exist in the database.
 * The @ResponseStatus annotation ensures that Spring automatically returns
 * an HTTP 404 NOT FOUND status when this exception is handled.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
