package com.example_jelle.backenddico.dto.access;

import jakarta.validation.constraints.NotBlank;

public class GrantAccessRequestDto {
    @NotBlank
    private String accessCode;

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
