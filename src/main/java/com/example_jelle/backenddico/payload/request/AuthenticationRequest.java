package com.example_jelle.backenddico.payload.request;

/**
 * This class is a Data Transfer Object (DTO) for authentication requests.
 * It holds the credentials (email and password) submitted by a user for login.
 */
public class AuthenticationRequest {

    /**
     * The user's email for authentication.
     */
    private String email;
    /**
     * The user's password.
     */
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
