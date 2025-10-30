// Exception thrown when a user is not found in the database.
package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    // Constructs a new UserNotFoundException with the specified detail message.
    public UserNotFoundException(String message) {
        super(message);
    }
}
