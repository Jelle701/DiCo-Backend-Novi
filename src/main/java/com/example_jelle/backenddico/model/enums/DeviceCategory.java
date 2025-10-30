// Enum representing the different categories of diabetic devices.
package com.example_jelle.backenddico.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

public enum DeviceCategory {
    // Represents an insulin pump.
    POMP("pomp", "insulinPump"),
    // Represents a Continuous Glucose Monitor (CGM).
    CGM("cgm"),
    // Represents a standard blood glucose meter.
    METER("meter", "bloodGlucoseMeter");

    private final String[] values;

    // Constructs a new DeviceCategory.
    DeviceCategory(String... values) {
        this.values = values;
    }

    // Custom deserializer for Jackson.
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
