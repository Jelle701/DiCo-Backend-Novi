// Data Transfer Object for user signup requests.
package com.example_jelle.backenddico.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    private String username;

    @NotBlank(message = "Email is required.")
    @Size(max = 50, message = "Email cannot be longer than 50 characters.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters.")
    private String password;

    private Set<String> role;

    // Gets the username.
    public String getUsername() {
        return username;
    }

    // Sets the username.
    public void setUsername(String username) {
        this.username = username;
    }

    // Gets the email.
    public String getEmail() {
        return email;
    }

    // Sets the email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Gets the password.
    public String getPassword() {
        return password;
    }

    // Sets the password.
    public void setPassword(String password) {
        this.password = password;
    }

    // Gets the user roles.
    public Set<String> getRole() {
        return this.role;
    }

    // Sets the user roles.
    public void setRole(Set<String> role) {
        this.role = role;
    }
}
