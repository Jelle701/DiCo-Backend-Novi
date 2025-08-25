package com.example_jelle.backenddico.dto.user;

import java.util.Set;

/**
 * This class is a Data Transfer Object (DTO) for a user's full profile information.
 * It is used to send comprehensive user details, including username, email, and assigned roles,
 * to the client or between different services.
 */
public class FullUserProfileDto {

    private String username;
    private String email;
    private Set<String> roles;

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
