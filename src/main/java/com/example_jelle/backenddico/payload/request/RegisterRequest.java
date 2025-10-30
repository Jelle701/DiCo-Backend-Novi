// Data Transfer Object for user registration requests.
package com.example_jelle.backenddico.payload.request;

import com.example_jelle.backenddico.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Role role;

    // Gets the user's email.
    public String getEmail() {
        return email;
    }

    // Sets the user's email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Gets the user's password.
    public String getPassword() {
        return password;
    }

    // Sets the user's password.
    public void setPassword(String password) {
        this.password = password;
    }

    // Gets the user's role.
    public Role getRole() {
        return role;
    }

    // Sets the user's role.
    public void setRole(Role role) {
        this.role = role;
    }
}
