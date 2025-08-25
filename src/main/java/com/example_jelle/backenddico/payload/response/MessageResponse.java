package com.example_jelle.backenddico.payload.response;

/**
 * This class is a generic Data Transfer Object (DTO) for sending a simple message as a response.
 * It is used for API endpoints that need to return a straightforward status or confirmation message.
 */
public class MessageResponse {
    /**
     * The message to be sent in the response.
     */
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
