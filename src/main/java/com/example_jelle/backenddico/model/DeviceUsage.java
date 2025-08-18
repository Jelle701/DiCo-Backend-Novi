package com.example_jelle.backenddico.model;

import jakarta.persistence.*; // <-- Correcte import

@Entity
public class DeviceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean usesCGM;
    private Boolean usesInsulinPump;

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