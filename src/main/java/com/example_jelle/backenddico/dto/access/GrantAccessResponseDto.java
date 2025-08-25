package com.example_jelle.backenddico.dto.access;

/**
 * This class is a Data Transfer Object (DTO) for the response to an access grant request.
 * It contains the delegated token and the username of the patient to whom access has been granted.
 */
public class GrantAccessResponseDto {
    private String delegatedToken;
    private String patientUsername;

    public GrantAccessResponseDto(String delegatedToken, String patientUsername) {
        this.delegatedToken = delegatedToken;
        this.patientUsername = patientUsername;
    }

    public String getDelegatedToken() {
        return delegatedToken;
    }

    public void setDelegatedToken(String delegatedToken) {
        this.delegatedToken = delegatedToken;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }
}
