package com.example_jelle.backenddico.dto.provider;

public class DelegatedTokenResponseDto {
    private String delegatedToken;
    private String patientUsername;

    public DelegatedTokenResponseDto(String delegatedToken, String patientUsername) {
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
