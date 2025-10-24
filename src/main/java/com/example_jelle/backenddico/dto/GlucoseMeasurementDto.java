package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.MeasurementSource;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.ZonedDateTime;

public class GlucoseMeasurementDto {

    private Long id;

    @NotNull(message = "Glucose value is required.")
    @Positive(message = "Glucose value must be a positive number.")
    private Double value;

    @NotNull(message = "Timestamp is required.")
    private ZonedDateTime timestamp;

    private MeasurementSource source;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public ZonedDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(ZonedDateTime timestamp) { this.timestamp = timestamp; }
    public MeasurementSource getSource() { return source; }
    public void setSource(MeasurementSource source) { this.source = source; }

    public static GlucoseMeasurementDto fromEntity(GlucoseMeasurement measurement) {
        GlucoseMeasurementDto dto = new GlucoseMeasurementDto();
        dto.setId(measurement.getId());
        dto.setValue(measurement.getValue());
        dto.setTimestamp(measurement.getTimestamp());
        dto.setSource(measurement.getSource());
        return dto;
    }

    public GlucoseMeasurement toEntity() {
        GlucoseMeasurement measurement = new GlucoseMeasurement();
        measurement.setValue(this.value);
        measurement.setTimestamp(this.timestamp);

        // Map MANUAL_ENTRY to MANUAL_UPLOAD to satisfy the database constraint
        if (this.source == MeasurementSource.MANUAL_ENTRY) {
            measurement.setSource(MeasurementSource.MANUAL_UPLOAD);
        } else {
            measurement.setSource(this.source);
        }
        return measurement;
    }
}
