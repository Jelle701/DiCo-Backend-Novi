// Entity representing a single piece of health-related data for a user.
package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "health_data")
public class HealthData {

    // The unique identifier for this health data record.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user to whom this health data belongs.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // A string that defines the type of health data (e.g., "steps", "heart_rate").
    @Column(nullable = false)
    private String dataType;

    // The exact timestamp when the data was recorded.
    @Column(nullable = false)
    private Instant timestamp;

    // The numerical value of the measurement.
    @Column(nullable = false)
    private double value;

    // Gets the ID of the health data record.
    public Long getId() {
        return id;
    }

    // Sets the ID of the health data record.
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the user associated with this health data.
    public User getUser() {
        return user;
    }

    // Sets the user associated with this health data.
    public void setUser(User user) {
        this.user = user;
    }

    // Gets the type of health data.
    public String getDataType() {
        return dataType;
    }

    // Sets the type of health data.
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    // Gets the timestamp of the health data.
    public Instant getTimestamp() {
        return timestamp;
    }

    // Sets the timestamp of the health data.
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    // Gets the value of the health data.
    public double getValue() {
        return value;
    }

    // Sets the value of the health data.
    public void setValue(double value) {
        this.value = value;
    }
}
