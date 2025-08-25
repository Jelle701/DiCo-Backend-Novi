package com.example_jelle.backenddico.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

/**
 * This enum represents the gender options for a user profile.
 * It is designed to handle multiple string representations for each gender (e.g., in different languages)
 * and provides a custom deserializer for flexibility when processing incoming data.
 */
public enum Gender {
    MALE("Male", "Man"),
    FEMALE("Female", "Vrouw"),
    OTHER("Other", "Anders"),
    PREFER_NOT_TO_SAY("Prefer not to say", "Zeg ik liever niet");

    private final String[] values;

    Gender(String... values) {
        this.values = values;
    }

    /**
     * This method is a custom deserializer for Jackson (used for JSON processing).
     * It allows the enum to be created from a string value that matches any of the defined aliases,
     * ignoring case.
     * @param value The string value from the incoming JSON.
     * @return The matching Gender enum constant, or null if no match is found.
     */
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
