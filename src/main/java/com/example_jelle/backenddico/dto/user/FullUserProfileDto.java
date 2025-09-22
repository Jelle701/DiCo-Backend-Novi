package com.example_jelle.backenddico.dto.user;

import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserFlags;

/**
 * This class is a Data Transfer Object (DTO) for a user's full profile information.
 * It is structured to match the exact requirements of the frontend, including the critical 'flags' object
 * which controls the onboarding flow.
 */
public class FullUserProfileDto {

    private Long id;
    private String email;
    private String role;
    private UserFlags flags;

    /**
     * Default constructor.
     */
    public FullUserProfileDto() {
    }

    /**
     * Constructor to easily map from a User entity.
     * @param user The User entity to map from.
     */
    public FullUserProfileDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole() != null ? user.getRole().name() : null;
        this.flags = user.getFlags();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserFlags getFlags() {
        return flags;
    }

    public void setFlags(UserFlags flags) {
        this.flags = flags;
    }
}
