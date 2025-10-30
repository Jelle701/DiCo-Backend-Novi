// Embeddable object that holds various boolean flags for a user.
package com.example_jelle.backenddico.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserFlags {

    // Flag indicating whether the user has verified their email address.
    private boolean emailVerified = false;
    // Flag indicating whether the user has filled in their basic details.
    private boolean hasDetails = false;
    // Flag indicating whether the user has saved their preferences.
    private boolean hasPreferences = false;

    // Checks if the user's email is verified.
    public boolean isEmailVerified() {
        return emailVerified;
    }

    // Sets the email verification status.
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    // Checks if the user has filled in their details.
    public boolean isHasDetails() {
        return hasDetails;
    }

    // Sets the status of whether the user has filled in their details.
    public void setHasDetails(boolean hasDetails) {
        this.hasDetails = hasDetails;
    }

    // Checks if the user has saved their preferences.
    public boolean isHasPreferences() {
        return hasPreferences;
    }

    // Sets the status of whether the user has saved their preferences.
    public void setHasPreferences(boolean hasPreferences) {
        this.hasPreferences = hasPreferences;
    }
}
