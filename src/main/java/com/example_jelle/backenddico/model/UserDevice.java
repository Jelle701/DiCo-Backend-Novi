// Entity representing a specific device associated with a user.
package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_devices")
public class UserDevice {
    // The unique identifier for the user device record.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who owns this device.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The category of the device (e.g., "PUMP", "SENSOR").
    private String category;
    // The manufacturer of the device.
    private String manufacturer;
    // The model name or number of the device.
    private String model;

    // Gets the ID of the user device.
    public Long getId() { return id; }
    // Sets the ID of the user device.
    public void setId(Long id) { this.id = id; }

    // Gets the user associated with this device.
    public User getUser() { return user; }
    // Sets the user associated with this device.
    public void setUser(User user) { this.user = user; }

    // Gets the category of the device.
    public String getCategory() { return category; }
    // Sets the category of the device.
    public void setCategory(String category) { this.category = category; }

    // Gets the manufacturer of the device.
    public String getManufacturer() { return manufacturer; }
    // Sets the manufacturer of the device.
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    // Gets the model of the device.
    public String getModel() { return model; }
    // Sets the model of the device.
    public void setModel(String model) { this.model = model; }
}
