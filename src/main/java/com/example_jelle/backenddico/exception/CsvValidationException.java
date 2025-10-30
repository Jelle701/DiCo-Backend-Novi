// Exception thrown when there is a validation error during CSV processing.
package com.example_jelle.backenddico.exception;

public class CsvValidationException extends RuntimeException {
    // Constructs a new CsvValidationException with the specified detail message.
    public CsvValidationException(String message) {
        super(message);
    }
}
