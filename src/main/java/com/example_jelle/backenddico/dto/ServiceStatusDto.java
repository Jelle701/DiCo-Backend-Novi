// Data Transfer Object representing the status of a user's connection to an external service.
package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.model.enums.ServiceName;
import com.example_jelle.backenddico.model.UserServiceConnection;

import java.time.ZonedDateTime;

public class ServiceStatusDto {

    private ServiceName serviceName;
    private boolean isConnected;
    private String email; // The email associated with the service connection
    private ZonedDateTime lastSync;

    // Constructs a new ServiceStatusDto.
    public ServiceStatusDto(ServiceName serviceName, boolean isConnected, String email, ZonedDateTime lastSync) {
        this.serviceName = serviceName;
        this.isConnected = isConnected;
        this.email = email;
        this.lastSync = lastSync;
    }

    // Creates a ServiceStatusDto from a UserServiceConnection entity.
    public static ServiceStatusDto fromConnection(UserServiceConnection connection) {
        return new ServiceStatusDto(
                connection.getServiceName(),
                true,
                connection.getEmail(),
                connection.getLastSync()
        );
    }

    // Creates a ServiceStatusDto for a disconnected service.
    public static ServiceStatusDto disconnected(ServiceName serviceName) {
        return new ServiceStatusDto(serviceName, false, null, null);
    }

    // Gets the name of the service.
    public ServiceName getServiceName() {
        return serviceName;
    }

    // Sets the name of the service.
    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    // Checks if the service is connected.
    public boolean isConnected() {
        return isConnected;
    }

    // Sets the connection status of the service.
    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    // Gets the email associated with the service connection.
    public String getEmail() {
        return email;
    }

    // Sets the email associated with the service connection.
    public void setEmail(String email) {
        this.email = email;
    }

    // Gets the last synchronization timestamp.
    public ZonedDateTime getLastSync() {
        return lastSync;
    }

    // Sets the last synchronization timestamp.
    public void setLastSync(ZonedDateTime lastSync) {
        this.lastSync = lastSync;
    }
}
