// Data Transfer Object for glucose measurement data.
package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.enums.MeasurementSource;
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

    // Gets the ID of the glucose measurement.
    public Long getId() { return id; }
    // Sets the ID of the glucose measurement.
    public void setId(Long id) { this.id = id; }
    // Gets the glucose value.
    public Double getValue() { return value; }
    // Sets the glucose value.
    public void setValue(Double value) { this.value = value; }
    // Gets the timestamp of the measurement.
    public ZonedDateTime getTimestamp() { return timestamp; }
    // Sets the timestamp of the measurement.
    public void setTimestamp(ZonedDateTime timestamp) { this.timestamp = timestamp; }
    // Gets the source of the measurement.
    public MeasurementSource getSource() { return source; }
    // Sets the source of the measurement.
    public void setSource(MeasurementSource source) { this.source = source; }

    // Converts a GlucoseMeasurement entity to a GlucoseMeasurementDto.
    public static GlucoseMeasurementDto fromEntity(GlucoseMeasurement measurement) {
        GlucoseMeasurementDto dto = new GlucoseMeasurementDto();
        dto.setId(measurement.getId());
        dto.setValue(measurement.getValue());
        dto.setTimestamp(measurement.getTimestamp());
        dto.setSource(measurement.getSource());
        return dto;
    }

    // Converts the GlucoseMeasurementDto to a GlucoseMeasurement entity.
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
