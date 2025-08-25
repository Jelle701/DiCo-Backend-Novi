package com.example_jelle.backenddico.dto.access;

import jakarta.validation.constraints.NotBlank;

/**
 * This class is a Data Transfer Object (DTO) for access grant requests.
 * It is used to transport the access code from the client to the server.
 */
public class GrantAccessRequestDto {
    /**
     * The access code provided by the user to gain access.
     * It must not be blank.
     */
    @NotBlank
    private String accessCode;

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
