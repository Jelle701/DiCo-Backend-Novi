// Generic exception thrown when a specific record or entity cannot be found.
package com.example_jelle.backenddico.exception;

public class RecordNotFoundException extends RuntimeException {
    // Constructs a new RecordNotFoundException with the specified detail message.
    public RecordNotFoundException(String message) {
        super(message);
    }
}
