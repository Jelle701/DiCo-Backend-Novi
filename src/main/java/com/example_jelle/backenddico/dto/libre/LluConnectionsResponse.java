// Response model for a LibreLinkUp connections request.
package com.example_jelle.backenddico.dto.libre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LluConnectionsResponse(List<ConnectionData> data) {

    // Represents the data for a single connection.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ConnectionData(String patientId, String firstName, String lastName) {
    }
}
