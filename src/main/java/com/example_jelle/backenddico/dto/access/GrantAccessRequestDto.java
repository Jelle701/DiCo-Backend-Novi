// Data Transfer Object for access grant requests.
package com.example_jelle.backenddico.dto.access;

import jakarta.validation.constraints.NotBlank;

public class GrantAccessRequestDto {
    // The access code provided by the user to gain access.
    @NotBlank
    private String accessCode;

    // Gets the access code.
    public String getAccessCode() {
        return accessCode;
    }

    // Sets the access code.
    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
