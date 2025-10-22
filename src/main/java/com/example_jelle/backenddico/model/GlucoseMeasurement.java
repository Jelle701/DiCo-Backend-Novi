package com.example_jelle.backenddico.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * This entity represents a single glucose measurement taken by a user.
 * It stores the glucose value and the precise timestamp of the measurement.
 */
@Entity
@Table(name = "glucose_measurements",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"user_id", "timestamp", "source"})
       })
public class GlucoseMeasurement {

    /**
     * The unique identifier for the glucose measurement record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who took this measurement.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    /**
     * The glucose value, e.g., in mmol/L or mg/dL.
     */
    @Column(nullable = false)
    private Double value;

    /**
     * The exact timestamp when the measurement was taken.
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * The type of measurement (e.g., fasting, postprandial).
     */
    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;

    /**
     * The source of the measurement (e.g., manual input, LibreView sync).
     * Defaults to MANUAL for existing records.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'MANUAL'")
    private MeasurementSource source = MeasurementSource.MANUAL;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public MeasurementSource getSource() {
        return source;
    }

    public void setSource(MeasurementSource source) {
        this.source = source;
    }
}
