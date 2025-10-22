package com.example_jelle.backenddico.dto.health;

// import lombok.Data; // Tijdelijk verwijderd
import java.time.Instant;

/**
 * This class is a generic Data Transfer Object (DTO) for a single data point in a time series.
 * It is used to represent any measurement that has a specific value at a specific point in time.
 */
// @Data // Tijdelijk verwijderd
public class DataPointDto {
    /**
     * The exact moment in time when the data point was recorded.
     */
    private Instant timestamp;
    /**
     * The numerical value of the data point (e.g., heart rate, step count).
     */
    private Double value;

    // Handmatige getters en setters
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
