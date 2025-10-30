// Data Transfer Object for authentication requests.
package com.example_jelle.backenddico.payload.request;

public class AuthenticationRequest {

    private String username;
    private String password;

    // Gets the username.
    public String getUsername() {
        return username;
    }

    // Sets the username.
    public void setUsername(String username) {
        this.username = username;
    }

    // Gets the password.
    public String getPassword() {
        return password;
    }

    // Sets the password.
    public void setPassword(String password) {
        this.password = password;
    }
}
