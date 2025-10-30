// Data Transfer Object for the response to an access grant request.
package com.example_jelle.backenddico.dto.access;

public class GrantAccessResponseDto {
    private String delegatedToken;
    private String patientUsername;

    // Constructs a new GrantAccessResponseDto.
    public GrantAccessResponseDto(String delegatedToken, String patientUsername) {
        this.delegatedToken = delegatedToken;
        this.patientUsername = patientUsername;
    }

    // Gets the delegated token.
    public String getDelegatedToken() {
        return delegatedToken;
    }

    // Sets the delegated token.
    public void setDelegatedToken(String delegatedToken) {
        this.delegatedToken = delegatedToken;
    }

    // Gets the patient's username.
    public String getPatientUsername() {
        return patientUsername;
    }

    // Sets the patient's username.
    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }
}
