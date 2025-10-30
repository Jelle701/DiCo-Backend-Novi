// Exception thrown when there is an issue with file uploads.
package com.example_jelle.backenddico.exception;

public class FileUploadException extends RuntimeException {
    // Constructs a new FileUploadException with the specified detail message.
    public FileUploadException(String message) {
        super(message);
    }
}
