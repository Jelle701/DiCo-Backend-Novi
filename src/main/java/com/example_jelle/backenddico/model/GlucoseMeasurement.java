package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * This entity represents a single glucose measurement taken by a user.
 * It stores the glucose value and the precise timestamp of the measurement.
 */
@Entity
@Table(name = "glucose_measurements")
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
}
