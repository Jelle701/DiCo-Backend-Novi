package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

/**
 * This entity represents a specific device associated with a user.
 * It stores details about the device such as its category, manufacturer, and model.
 */
@Entity
@Table(name = "user_devices")
public class UserDevice {
    /**
     * The unique identifier for the user device record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who owns this device.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The category of the device (e.g., "PUMP", "SENSOR").
     */
    private String category;
    /**
     * The manufacturer of the device.
     */
    private String manufacturer;
    /**
     * The model name or number of the device.
     */
    private String model;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}
