// Exception thrown when an attempt is made to create a user with existing credentials.
package com.example_jelle.backenddico.exception;

public class UserAlreadyExistsException extends RuntimeException {
    // Constructs a new UserAlreadyExistsException with the specified detail message.
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
