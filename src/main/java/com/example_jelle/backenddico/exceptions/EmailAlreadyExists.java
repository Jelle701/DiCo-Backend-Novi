package com.example_jelle.backenddico.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Deze annotatie zorgt ervoor dat Spring automatisch een 409 CONFLICT status teruggeeft.
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists(String message) {
        super(message);
    }
}