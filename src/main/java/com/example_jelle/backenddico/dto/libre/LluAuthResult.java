package com.example_jelle.backenddico.dto.libre;

/**
 * Represents the internal, successful result of a LibreView authentication.
 * It contains the core data needed for subsequent server-side operations.
 */
public record LluAuthResult(String token, String userId, String patientId) {
}
