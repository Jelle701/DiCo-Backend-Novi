package com.example_jelle.backenddico.exception;

import com.example_jelle.backenddico.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * This class acts as a global exception handler for the entire application.
 * Using @RestControllerAdvice, it intercepts exceptions thrown from any controller
 * and translates them into appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles the EmailAlreadyExists exception.
     * @param ex The exception instance.
     * @param request The web request.
     * @return A ResponseEntity with a 409 CONFLICT status and an error message.
     */
    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<MessageResponse> handleEmailAlreadyExistsException(EmailAlreadyExists ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles the InvalidTokenException.
     * @param ex The exception instance.
     * @param request The web request.
     * @return A ResponseEntity with a 400 BAD REQUEST status and an error message.
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<MessageResponse> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the UnauthorizedException.
     * @param ex The exception instance.
     * @param request The web request.
     * @return A ResponseEntity with a 401 UNAUTHORIZED status and an error message.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<MessageResponse> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles authorization failures (AccessDeniedException).
     * This catches cases where a user is authenticated but lacks the necessary permissions for an action.
     * @param ex The exception instance.
     * @param request The web request.
     * @return A ResponseEntity with a 403 FORBIDDEN status and a clear error message.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        MessageResponse messageResponse = new MessageResponse("Access Denied: You do not have permission to perform this action.");
        return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles validation exceptions from @Valid annotations on controller method arguments.
     * It collects all validation errors into a map of field names to error messages.
     * @param ex The MethodArgumentNotValidException instance.
     * @return A ResponseEntity with a 400 BAD REQUEST status and a map of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * A catch-all handler for any other unhandled exceptions.
     * This prevents stack traces from being exposed to the client and provides a generic server error message.
     * @param ex The exception instance.
     * @param request The web request.
     * @return A ResponseEntity with a 500 INTERNAL SERVER ERROR status and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleAllOtherExceptions(Exception ex, WebRequest request) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        MessageResponse messageResponse = new MessageResponse("An internal server error occurred. Please try again later.");
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
