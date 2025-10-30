// Entity representing a connection between a user and an external service.
package com.example_jelle.backenddico.model;

import com.example_jelle.backenddico.model.enums.ServiceName;
import com.example_jelle.backenddico.security.CryptoConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

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

    // Encrypted field for service-specific credentials.
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

    // Gets the ID of the service connection.
    public Long getId() {
        return id;
    }

    // Sets the ID of the service connection.
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the user associated with this service connection.
    public User getUser() {
        return user;
    }

    // Sets the user associated with this service connection.
    public void setUser(User user) {
        this.user = user;
    }

    // Gets the name of the service.
    public ServiceName getServiceName() {
        return serviceName;
    }

    // Sets the name of the service.
    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    // Gets the email for the service.
    public String getEmail() {
        return email;
    }

    // Sets the email for the service.
    public void setEmail(String email) {
        this.email = email;
    }

    // Gets the password for the service.
    public String getPassword() {
        return password;
    }

    // Sets the password for the service.
    public void setPassword(String password) {
        this.password = password;
    }

    // Gets the access token for the service.
    public String getAccessToken() {
        return accessToken;
    }

    // Sets the access token for the service.
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    // Gets the refresh token for the service.
    public String getRefreshToken() {
        return refreshToken;
    }

    // Sets the refresh token for the service.
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Gets the external user ID.
    public String getExternalUserId() {
        return externalUserId;
    }

    // Sets the external user ID.
    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    // Gets the external patient ID.
    public String getExternalPatientId() {
        return externalPatientId;
    }

    // Sets the external patient ID.
    public void setExternalPatientId(String externalPatientId) {
        this.externalPatientId = externalPatientId;
    }

    // Gets the last sync timestamp.
    public ZonedDateTime getLastSync() {
        return lastSync;
    }

    // Sets the last sync timestamp.
    public void setLastSync(ZonedDateTime lastSync) {
        this.lastSync = lastSync;
    }
}
