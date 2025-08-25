package com.example_jelle.backenddico.payload.request;

/**
 * This class is a Data Transfer Object (DTO) for user registration requests.
 * It contains the essential fields (email and password) needed to create a new user account.
 */
public class RegisterRequest {
    /**
     * The email address for the new account.
     */
    private String email;
    /**
     * The password for the new account.
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
