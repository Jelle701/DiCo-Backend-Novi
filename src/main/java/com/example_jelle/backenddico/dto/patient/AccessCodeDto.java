package com.example_jelle.backenddico.dto.patient;

/**
 * This class is a Data Transfer Object (DTO) for an access code.
 * It is used to send an access code to the client.
 */
public class AccessCodeDto {
    private String accessCode;

    public AccessCodeDto(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
