package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

/**
 * This class is a Data Transfer Object (DTO) for glucose measurements.
 * It is used for creating and retrieving glucose measurement data and includes validation constraints.
 */
public class GlucoseMeasurementDto {

    private Long id;

    @NotNull(message = "Glucose value is required.")
    @Positive(message = "Glucose value must be a positive number.")
    private Double value;

    @NotNull(message = "Timestamp is required.")
    private LocalDateTime timestamp;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    /**
     * A static factory method to create a DTO from a GlucoseMeasurement entity.
     * @param measurement The entity to convert.
     * @return A new GlucoseMeasurementDto populated with the entity's data.
     */
    public static GlucoseMeasurementDto fromEntity(GlucoseMeasurement measurement) {
        GlucoseMeasurementDto dto = new GlucoseMeasurementDto();
        dto.setId(measurement.getId());
        dto.setValue(measurement.getValue());
        dto.setTimestamp(measurement.getTimestamp());
        return dto;
    }

    /**
     * Converts this DTO to a GlucoseMeasurement entity.
     * @return A new GlucoseMeasurement entity populated with this DTO's data.
     */
    public GlucoseMeasurement toEntity() {
        GlucoseMeasurement measurement = new GlucoseMeasurement();
        measurement.setValue(this.value);
        measurement.setTimestamp(this.timestamp);
        return measurement;
    }
}
