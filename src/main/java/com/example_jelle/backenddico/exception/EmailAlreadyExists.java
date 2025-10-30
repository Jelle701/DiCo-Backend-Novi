// Exception thrown when an email address is already in use.
package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExists extends RuntimeException {
    // Constructs a new EmailAlreadyExists exception with the specified detail message.
    public EmailAlreadyExists(String message) {
        super(message);
    }
}
