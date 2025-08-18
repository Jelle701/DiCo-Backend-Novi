package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.NotBlank;

public class VerifyRequest {

    @NotBlank(message = "Token is required.")
    private String token;

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}