package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.UserProfile;

/**
 * This class is a Data Transfer Object (DTO) for a user's full profile.
 * It includes basic user identification (username, email) along with the detailed UserProfile.
 */
public class FullUserProfileDto {
    private String username;
    private String email;
    private UserProfile userProfile;

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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
