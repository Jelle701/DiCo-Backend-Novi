// Data Transfer Object for basic user information.
package com.example_jelle.backenddico.dto;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private String role;

    // Gets the user's ID.
    public Long getId() {
        return id;
    }

    // Sets the user's ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the user's username.
    public String getUsername() {
        return username;
    }

    // Sets the user's username.
    public void setUsername(String username) {
        this.username = username;
    }

    // Gets the user's email.
    public String getEmail() {
        return email;
    }

    // Sets the user's email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Checks if the user account is enabled.
    public boolean isEnabled() {
        return enabled;
    }

    // Sets whether the user account is enabled.
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // Gets the user's role.
    public String getRole() {
        return role;
    }

    // Sets the user's role.
    public void setRole(String role) {
        this.role = role;
    }
}
