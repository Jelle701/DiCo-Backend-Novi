// Data Transfer Object for account verification requests.
package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.NotBlank;

public class VerifyRequest {

    @NotBlank(message = "Token is required.")
    private String token;

    // Gets the verification token.
    public String getToken() {
        return token;
    }

    // Sets the verification token.
    public void setToken(String token) {
        this.token = token;
    }
}
