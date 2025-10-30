// Data Transfer Object for an access code.
package com.example_jelle.backenddico.dto.patient;

public class AccessCodeDto {
    private String accessCode;

    // Constructs a new AccessCodeDto.
    public AccessCodeDto(String accessCode) {
        this.accessCode = accessCode;
    }

    // Gets the access code.
    public String getAccessCode() {
        return accessCode;
    }

    // Sets the access code.
    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
