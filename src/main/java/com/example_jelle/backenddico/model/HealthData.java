package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * This entity represents a single piece of health-related data for a user.
 * It's a generic structure designed to store time-series data, where the type of data
 * is identified by the `dataType` field (e.g., "steps", "heart_rate").
 */
@Entity
@Table(name = "health_data")
public class HealthData {

    /**
     * The unique identifier for this health data record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user to whom this health data belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * A string that defines the type of health data (e.g., "steps", "heart_rate").
     */
    @Column(nullable = false)
    private String dataType;

    /**
     * The exact timestamp when the data was recorded.
     */
    @Column(nullable = false)
    private Instant timestamp;

    /**
     * The numerical value of the measurement.
     */
    @Column(nullable = false)
    private double value;

    // Getters and setters
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
