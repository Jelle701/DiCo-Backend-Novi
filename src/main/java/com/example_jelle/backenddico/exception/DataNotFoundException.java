// Exception thrown when requested data is not found.
package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    // Constructs a new DataNotFoundException with the specified detail message.
    public DataNotFoundException(String message) {
        super(message);
    }
}
