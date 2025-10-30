// Data Transfer Object for user login requests.
package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // Gets the user's email.
    public String getEmail() { return email; }
    // Sets the user's email.
    public void setEmail(String email) { this.email = email; }
    // Gets the user's password.
    public String getPassword() { return password; }
    // Sets the user's password.
    public void setPassword(String password) { this.password = password; }
}
