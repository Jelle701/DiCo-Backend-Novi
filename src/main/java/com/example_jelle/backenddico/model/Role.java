package com.example_jelle.backenddico.model;

/**
 * This enum represents the roles a user can have within the application.
 * The roles define the user's permissions and capabilities.
 */
public enum Role {
    /**
     * A patient, the primary user of the application who tracks their health data.
     */
    PATIENT,
    /**
     * A guardian, who can monitor a patient's data.
     */
    GUARDIAN,
    /**
     * A healthcare provider, who can view data for multiple linked patients.
     */
    PROVIDER
}
