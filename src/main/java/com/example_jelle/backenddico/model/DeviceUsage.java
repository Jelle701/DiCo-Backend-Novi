package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

/**
 * This entity tracks a user's usage of specific categories of diabetic devices.
 * It holds boolean flags to indicate whether a user utilizes a CGM or an insulin pump.
 */
@Entity
public class DeviceUsage {
    /**
     * The unique identifier for the device usage record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Flag indicating if the user uses a Continuous Glucose Monitor (CGM).
     */
    private Boolean usesCGM;
    /**
     * Flag indicating if the user uses an insulin pump.
     */
    private Boolean usesInsulinPump;

    /**
     * The user associated with this device usage information.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and setters ...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUsesCGM() {
        return usesCGM;
    }

    public void setUsesCGM(Boolean usesCGM) {
        this.usesCGM = usesCGM;
    }

    public Boolean getUsesInsulinPump() {
        return usesInsulinPump;
    }

    public void setUsesInsulinPump(Boolean usesInsulinPump) {
        this.usesInsulinPump = usesInsulinPump;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
