package com.example_jelle.backenddico.dto.libre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents the full login response from the LibreView API.
 * It includes the status, and a data object which can contain an auth ticket, user info, or a required next step.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LluLoginResponse(int status, LluData data) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LluData(AuthTicket authTicket, User user, Step step) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AuthTicket(String token, Long expires, Long duration) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record User(String id) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Step(String type) {}
}
