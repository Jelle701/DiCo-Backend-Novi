package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.PastOrPresent; // VERWIJDERD
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class GlucoseMeasurementDto {

    private Long id;

    @NotNull(message = "Glucosewaarde is verplicht.")
    @Positive(message = "Glucosewaarde moet een positief getal zijn.")
    private Double value;

    @NotNull(message = "Tijdstip is verplicht.")
    // @PastOrPresent(message = "Tijdstip kan niet in de toekomst liggen.") // VERWIJDERD
    private LocalDateTime timestamp;

    // Getters en Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    /**
     * Factory-methode om een DTO te maken van een Entity.
     */
    public static GlucoseMeasurementDto fromEntity(GlucoseMeasurement measurement) {
        GlucoseMeasurementDto dto = new GlucoseMeasurementDto();
        dto.setId(measurement.getId());
        dto.setValue(measurement.getValue());
        dto.setTimestamp(measurement.getTimestamp());
        return dto;
    }

    /**
     * Methode om een Entity te maken van deze DTO.
     */
    public GlucoseMeasurement toEntity() {
        GlucoseMeasurement measurement = new GlucoseMeasurement();
        measurement.setValue(this.value);
        measurement.setTimestamp(this.timestamp);
        return measurement;
    }
}