package com.example_jelle.backenddico.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This enum represents the different categories of diabetic devices.
 * The @JsonProperty annotation ensures that the JSON string (lowercase)
 * is correctly converted to the Java Enum constant (UPPERCASE) during deserialization.
 */
public enum DeviceCategory {
    /**
     * Represents an insulin pump.
     */
    @JsonProperty("pomp")
    POMP,
    /**
     * Represents a Continuous Glucose Monitor (CGM).
     */
    @JsonProperty("cgm")
    CGM,
    /**
     * Represents a standard blood glucose meter.
     */
    @JsonProperty("meter")
    METER
}
