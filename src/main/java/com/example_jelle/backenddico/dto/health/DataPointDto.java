// Generic Data Transfer Object for a single data point in a time series.
package com.example_jelle.backenddico.dto.health;

import java.time.Instant;

public class DataPointDto {
    // The timestamp of the data point.
    private Instant timestamp;
    // The value of the data point.
    private Double value;

    // Gets the timestamp.
    public Instant getTimestamp() {
        return timestamp;
    }

    // Sets the timestamp.
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    // Gets the value.
    public Double getValue() {
        return value;
    }

    // Sets the value.
    public void setValue(Double value) {
        this.value = value;
    }
}
