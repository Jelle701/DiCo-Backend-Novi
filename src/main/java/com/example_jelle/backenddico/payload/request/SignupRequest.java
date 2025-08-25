package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

/**
 * This class is a Data Transfer Object (DTO) for user signup requests.
 * It encapsulates the necessary information for a new user to register, including username, email, password, and optional roles.
 */
public class SignupRequest {

    /**
     * The desired username for the new user.
     * It must not be blank and its size must be between 3 and 20 characters.
     */
    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    private String username;

    /**
     * The user's email address.
     * It must not be blank, cannot be longer than 50 characters, and must be a valid email format.
     */
    @NotBlank(message = "Email is required.")
    @Size(max = 50, message = "Email cannot be longer than 50 characters.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    /**
     * The desired password for the new user.
     * It must not be blank and its size must be between 6 and 40 characters.
     */
    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters.")
    private String password;

    /**
     * An optional set of roles to be assigned to the user upon registration (e.g., "user", "admin").
     * If roles are not specified, a default role can be assigned in the service layer.
     */
    private Set<String> role;

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
