package com.example_jelle.backenddico.exceptions;

public class CsvValidationException extends RuntimeException {
    public CsvValidationException(String message) {
        super(message);
    }
}
