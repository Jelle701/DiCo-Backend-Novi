package com.example_jelle.backenddico.dto;

// FIX: Import the correct UserFlags class
import com.example_jelle.backenddico.model.UserFlags;

/**
 * DTO voor het representeren van de status-flags van een gebruiker.
 */
public class UserFlagsDto {
    private boolean emailVerified;
    private boolean hasDetails;
    private boolean hasPreferences;

    // Getters en Setters
    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
    public boolean isHasDetails() { return hasDetails; }
    public void setHasDetails(boolean hasDetails) { this.hasDetails = hasDetails; }
    public boolean isHasPreferences() { return hasPreferences; }
    public void setHasPreferences(boolean hasPreferences) { this.hasPreferences = hasPreferences; }

    /**
     * Statische factory-methode om de DTO te vullen vanuit het Flags-object van de User.
     */
    // FIX: The parameter should be of type UserFlags, not a nested class.
    public static UserFlagsDto from(UserFlags flags) {
        UserFlagsDto dto = new UserFlagsDto();
        dto.setEmailVerified(flags.isEmailVerified());
        dto.setHasDetails(flags.isHasDetails());
        dto.setHasPreferences(flags.isHasPreferences());
        return dto;
    }
}