package com.example_jelle.backenddico.dto.guardian;

import jakarta.validation.constraints.NotBlank;

/**
 * This class is a Data Transfer Object (DTO) for requests to link a guardian or provider to a patient.
 * It carries the access code provided by the patient to authorize the connection.
 */
public class LinkPatientRequestDto {

    /**
     * The access code required to link to a patient's account.
     * This field must not be blank.
     */
    @NotBlank(message = "Access code is required.")
    private String accessCode;

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
