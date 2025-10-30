// Data Transfer Object for a user's full profile.
package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.UserProfile;

public class FullUserProfileDto {
    private String username;
    private String email;
    private UserProfile userProfile;

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

    // Gets the user profile.
    public UserProfile getUserProfile() {
        return userProfile;
    }

    // Sets the user profile.
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
