// Data Transfer Object for administrative user information.
package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.User;

import java.time.LocalDateTime;

public class AdminUserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private LocalDateTime createdAt;

    // Gets the user's ID.
    public Long getId() {
        return id;
    }

    // Sets the user's ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the user's email.
    public String getEmail() {
        return email;
    }

    // Sets the user's email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Gets the user's first name.
    public String getFirstName() {
        return firstName;
    }

    // Sets the user's first name.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Gets the user's last name.
    public String getLastName() {
        return lastName;
    }

    // Sets the user's last name.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Gets the user's role.
    public String getRole() {
        return role;
    }

    // Sets the user's role.
    public void setRole(String role) {
        this.role = role;
    }

    // Gets the creation timestamp of the user account.
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Sets the creation timestamp of the user account.
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Creates an AdminUserDto from a User object.
    public static AdminUserDto fromUser(User user) {
        AdminUserDto dto = new AdminUserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole().name());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
