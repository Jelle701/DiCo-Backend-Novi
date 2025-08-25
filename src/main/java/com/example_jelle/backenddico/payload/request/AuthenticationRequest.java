package com.example_jelle.backenddico.payload.request;

/**
 * This class is a Data Transfer Object (DTO) for authentication requests.
 * It holds the credentials (username and password) submitted by a user for login.
 */
public class AuthenticationRequest {

    /**
     * The username (typically the user's email) for authentication.
     */
    private String username;
    /**
     * The user's password.
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
