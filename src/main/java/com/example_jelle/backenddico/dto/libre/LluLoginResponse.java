// Response model for a LibreLinkUp login request.
package com.example_jelle.backenddico.dto.libre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LluLoginResponse(int status, LluData data) {

    // Represents the data returned from a login request.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LluData(AuthTicket authTicket, User user, Step step) {}

    // Represents the authentication ticket.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AuthTicket(String token, Long expires, Long duration) {}

    // Represents the user.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record User(String id) {}

    // Represents the next step in the authentication process.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Step(String type) {}
}
