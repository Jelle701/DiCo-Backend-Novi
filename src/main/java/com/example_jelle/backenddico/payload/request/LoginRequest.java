package com.jelle.dico.payload.request;

import jakarta.validation.constraints.NotBlank; // <-- Correcte import

public class LoginRequest {
    @NotBlank
    private String email; // Let op: je AuthController verwacht 'username', niet 'email'. Pas dit aan of je login-logica.

    @NotBlank
    private String password;

    // Getters en Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}