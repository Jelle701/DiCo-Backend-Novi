package com.example_jelle.backenddico.exception;

/**
 * This is a generic exception thrown when a specific record or entity
 * cannot be found in the database. It is a subclass of RuntimeException.
 * Unlike other custom exceptions in this package, it does not have a @ResponseStatus annotation,
 * meaning it will result in a 500 Internal Server Error unless caught by a specific handler.
 */
public class RecordNotFoundException extends RuntimeException {
    /**
     * Constructs a new RecordNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    public RecordNotFoundException(String message) {
        super(message);
    }
}
