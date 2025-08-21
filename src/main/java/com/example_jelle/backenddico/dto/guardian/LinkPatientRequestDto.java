package com.example_jelle.backenddico.dto.guardian;

import jakarta.validation.constraints.NotBlank;

public class LinkPatientRequestDto {

    @NotBlank(message = "Access code is required.")
    private String accessCode;

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
