package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.ServiceName;
import com.example_jelle.backenddico.model.UserServiceConnection;

import java.time.LocalDateTime;

/**
 * A DTO to represent the status of a user's connection to an external service.
 */
public class ServiceStatusDto {

    private ServiceName serviceName;
    private boolean isConnected;
    private String email; // The email associated with the service connection
    private LocalDateTime lastSync;

    public ServiceStatusDto(ServiceName serviceName, boolean isConnected, String email, LocalDateTime lastSync) {
        this.serviceName = serviceName;
        this.isConnected = isConnected;
        this.email = email;
        this.lastSync = lastSync;
    }

    // Factory method to create a DTO from a connection entity
    public static ServiceStatusDto fromConnection(UserServiceConnection connection) {
        return new ServiceStatusDto(
                connection.getServiceName(),
                true,
                connection.getEmail(),
                connection.getLastSync()
        );
    }

    // Factory method for a disconnected status
    public static ServiceStatusDto disconnected(ServiceName serviceName) {
        return new ServiceStatusDto(serviceName, false, null, null);
    }

    // Getters and Setters

    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLastSync() {
        return lastSync;
    }

    public void setLastSync(LocalDateTime lastSync) {
        this.lastSync = lastSync;
    }
}
