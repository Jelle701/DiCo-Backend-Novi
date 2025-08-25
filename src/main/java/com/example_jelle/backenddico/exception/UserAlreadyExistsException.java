package com.example_jelle.backenddico.exception;

/**
 * This exception is thrown when an attempt is made to create a user
 * with credentials (e.g., username or email) that already exist in the system.
 * It is a subclass of RuntimeException.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     * @param message The detail message.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
