// Exception thrown when an API request lacks valid authentication credentials.
package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    // Constructs a new UnauthorizedException with the specified detail message.
    public UnauthorizedException(String message) {
        super(message);
    }
}
