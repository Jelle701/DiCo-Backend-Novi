// Exception thrown when an attempt is made to create a user that already exists.
package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUserException extends RuntimeException {
    // Constructs a new DuplicateUserException with the specified detail message.
    public DuplicateUserException(String message) {
        super(message);
    }
}
