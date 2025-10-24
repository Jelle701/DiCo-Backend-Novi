package com.example_jelle.backenddico.model;

import com.example_jelle.backenddico.security.crypto.CryptoConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

/**
 * Represents a connection between a user and an external service (e.g., LibreView, Google Fit).
 * This entity stores the necessary credentials and metadata for the service integration.
 */
@Entity
@Table(name = "user_service_connections",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "service_name"}))
public class UserServiceConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_name", nullable = false)
    private ServiceName serviceName;

    // Store service-specific credentials (e.g., email, password, tokens) in an encrypted field.
    @Convert(converter = CryptoConverter.class)
    @Column(columnDefinition = "TEXT")
    private String email;

    @Convert(converter = CryptoConverter.class)
    @Column(columnDefinition = "TEXT")
    private String password; // For services that use password auth

    @Convert(converter = CryptoConverter.class)
    @Column(columnDefinition = "TEXT")
    private String accessToken;

    @Convert(converter = CryptoConverter.class)
    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @Column
    private String externalUserId; // e.g., the user ID from the external service

    @Column
    private String externalPatientId; // e.g., the patient ID from LibreView

    @Column
    private ZonedDateTime lastSync;

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

    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getExternalPatientId() {
        return externalPatientId;
    }

    public void setExternalPatientId(String externalPatientId) {
        this.externalPatientId = externalPatientId;
    }

    public ZonedDateTime getLastSync() {
        return lastSync;
    }

    public void setLastSync(ZonedDateTime lastSync) {
        this.lastSync = lastSync;
    }
}
