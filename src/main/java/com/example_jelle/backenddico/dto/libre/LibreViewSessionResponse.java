package com.example_jelle.backenddico.dto.libre;

/**
 * This DTO is returned to the frontend after a successful LibreView login.
 * It contains all necessary information for the client to make subsequent authenticated requests.
 */
public record LibreViewSessionResponse(String token, String userId, String patientId, String accountIdHash) {
}
