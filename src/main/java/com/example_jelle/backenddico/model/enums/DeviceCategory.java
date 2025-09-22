package com.example_jelle.backenddico.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

/**
 * This enum represents the different categories of diabetic devices.
 * It uses a custom deserializer to allow mapping from multiple string representations
 * (e.g., "pomp" or "insulinPump") to a single enum constant.
 */
public enum DeviceCategory {
    /**
     * Represents an insulin pump.
     * Now accepts both "pomp" and "insulinPump".
     */
    POMP("pomp", "insulinPump"),
    /**
     * Represents a Continuous Glucose Monitor (CGM).
     */
    CGM("cgm"),
    /**
     * Represents a standard blood glucose meter.
     * This now accepts both "meter" and "bloodGlucoseMeter" from the frontend.
     */
    METER("meter", "bloodGlucoseMeter");

    private final String[] values;

    DeviceCategory(String... values) {
        this.values = values;
    }

    /**
     * Custom deserializer for Jackson. It allows the enum to be created from a string value
     * that matches any of the defined aliases for each enum constant, ignoring case.
     * @param value The string value from the incoming JSON.
     * @return The matching DeviceCategory enum constant, or null if no match is found.
     */
    @JsonCreator
    public static DeviceCategory fromString(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(DeviceCategory.values())
                .filter(category -> Arrays.stream(category.values).anyMatch(val -> val.equalsIgnoreCase(value)))
                .findFirst()
                .orElse(null);
    }
}
