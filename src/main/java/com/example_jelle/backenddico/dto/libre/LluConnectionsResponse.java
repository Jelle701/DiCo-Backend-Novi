package com.example_jelle.backenddico.dto.libre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LluConnectionsResponse(List<ConnectionData> data) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ConnectionData(String patientId, String firstName, String lastName) {
    }
}
