package com.example_jelle.backenddico.model;

import jakarta.persistence.Embeddable;

/**
 * This class is an embeddable object that holds various boolean flags for a user.
 * It's designed to be embedded within another entity (like the User entity)
 * to track the completion status of different profile or onboarding steps.
 */
@Embeddable // Marks this class to be embeddable in another entity
public class UserFlags {

    /**
     * Flag indicating whether the user has verified their email address.
     */
    private boolean emailVerified = false;
    /**
     * Flag indicating whether the user has filled in their basic details.
     */
    private boolean hasDetails = false;
    /**
     * Flag indicating whether the user has saved their preferences.
     */
    private boolean hasPreferences = false;
    // The specification also mentions hasMedicineInfo and hasDevices, which can be added here later.

    // Getters and Setters
    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isHasDetails() {
        return hasDetails;
    }

    public void setHasDetails(boolean hasDetails) {
        this.hasDetails = hasDetails;
    }

    public boolean isHasPreferences() {
        return hasPreferences;
    }

    public void setHasPreferences(boolean hasPreferences) {
        this.hasPreferences = hasPreferences;
    }
}
