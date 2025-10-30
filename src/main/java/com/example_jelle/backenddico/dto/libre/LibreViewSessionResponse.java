// Response model for a LibreView session.
package com.example_jelle.backenddico.dto.libre;

public record LibreViewSessionResponse(String token, String userId, String patientId, String accountIdHash) {
}
