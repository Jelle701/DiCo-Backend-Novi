// Generic Data Transfer Object for sending a simple message as a response.
package com.example_jelle.backenddico.payload.response;

public class MessageResponse {
    // The message to be sent in the response.
    private String message;

    // Constructs a new MessageResponse.
    public MessageResponse(String message) {
        this.message = message;
    }

    // Gets the message.
    public String getMessage() { return message; }
    // Sets the message.
    public void setMessage(String message) { this.message = message; }
}
