package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * This class is a Data Transfer Object (DTO) for account verification requests.
 * It is used to transport the verification token from the client to the server.
 */
public class VerifyRequest {

    /**
     * The verification token (e.g., a code sent via email) required to verify the account.
     * This field must not be blank.
     */
    @NotBlank(message = "Token is required.")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
