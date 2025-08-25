package com.example_jelle.backenddico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when an API request lacks valid authentication credentials.
 * It is typically used in scenarios where authentication fails for reasons other than an invalid JWT,
 * such as a missing or incorrect API secret.
 * The @ResponseStatus annotation ensures that Spring automatically returns
 * an HTTP 401 UNAUTHORIZED status when this exception is handled.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     * @param message The detail message.
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
