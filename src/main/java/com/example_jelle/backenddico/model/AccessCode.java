package com.example_jelle.backenddico.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * This entity represents a temporary access code that a patient can generate.
 * Guardians or providers can use this code to link to the patient's account.
 * The code has an expiration time.
 */
@Entity
public class AccessCode {
    /**
     * The unique identifier for the access code.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique, randomly generated code string.
     */
    @Column(unique = true, nullable = false)
    private String code;

    /**
     * The timestamp when the code was created.
     */
    @Column(nullable = false)
    private LocalDateTime creationTime;

    /**
     * The timestamp when the code will expire and can no longer be used.
     */
    @Column(nullable = false)
    private LocalDateTime expirationTime;

    /**
     * The user (patient) who generated this access code.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
