// Enum representing the roles a user can have within the application.
package com.example_jelle.backenddico.model.enums;

public enum Role {
    // A patient, the primary user of the application.
    PATIENT,
    // A guardian, who can monitor a patient's data.
    GUARDIAN,
    // A healthcare provider, who can view data for multiple linked patients.
    PROVIDER,
    // An administrator with full access to the system.
    ADMIN
}
