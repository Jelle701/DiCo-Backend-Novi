package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * This class is a Data Transfer Object (DTO) for login requests.
 * It contains the credentials (email and password) submitted by a user during login.
 * Note: The field is named 'email', but the AuthController expects 'username'.
 * This might require an adjustment in the controller or here to ensure consistency.
 */
public class LoginRequest {
    /**
     * The user's email, used as the username for authentication.
     * This field must not be blank.
     */
    @NotBlank
    private String email;

    /**
     * The user's password.
     * This field must not be blank.
     */
    @NotBlank
    private String password;

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
