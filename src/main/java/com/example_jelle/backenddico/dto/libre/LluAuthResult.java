// Represents the successful result of a LibreView authentication.
package com.example_jelle.backenddico.dto.libre;

public record LluAuthResult(String token, String userId, String patientId) {
}
