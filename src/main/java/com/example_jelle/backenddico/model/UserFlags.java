// src/main/java/com/example_jelle/backenddico/model/UserFlags.java
package com.example_jelle.backenddico.model;

import jakarta.persistence.Embeddable;

@Embeddable // Markeer deze klasse als in te sluiten in een andere entiteit
public class UserFlags {

    private boolean emailVerified = false;
    private boolean hasDetails = false;
    private boolean hasPreferences = false;
    // De specificatie noemt ook hasMedicineInfo en hasDevices, die kunnen hier later worden toegevoegd.

    // Getters en Setters
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