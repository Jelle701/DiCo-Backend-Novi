package com.example_jelle.backenddico.exceptions; // <-- Typefout in package naam gecorrigeerd ('exeptions' -> 'exceptions')

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Deze annotatie zorgt ervoor dat Spring automatisch een 404 NOT FOUND status teruggeeft.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException { // <-- Klasse moet een exception zijn

    // Een constructor is nodig om een foutmelding mee te kunnen geven.
    public UserNotFoundException(String message) {
        super(message);
    }
}