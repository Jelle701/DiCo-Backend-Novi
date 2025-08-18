// Bestandslocatie: src/main/java/com/example_jelle/backenddico/payload/response/MessageResponse.java
package com.example_jelle.backenddico.payload.response; // <-- PACKAGE GECORRIGEERD

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}