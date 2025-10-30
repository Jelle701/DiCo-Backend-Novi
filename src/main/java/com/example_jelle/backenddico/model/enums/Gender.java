// Enum representing the gender options for a user profile.
package com.example_jelle.backenddico.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

public enum Gender {
    MALE("Male", "Man"),
    FEMALE("Female", "Vrouw"),
    OTHER("Other", "Anders"),
    PREFER_NOT_TO_SAY("Prefer not to say", "Zeg ik liever niet");

    private final String[] values;

    // Constructs a new Gender.
    Gender(String... values) {
        this.values = values;
    }

    // Custom deserializer for Jackson.
    @JsonCreator
    public static Gender fromString(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(Gender.values())
                .filter(gender -> Arrays.stream(gender.values).anyMatch(val -> val.equalsIgnoreCase(value)))
                .findFirst()
                .orElse(null);
    }
}
