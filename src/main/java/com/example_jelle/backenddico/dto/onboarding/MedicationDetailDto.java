package com.example_jelle.backenddico.dto.onboarding;

/**
 * This class is a Data Transfer Object (DTO) for medication details.
 * It holds information about the manufacturer and brand name of a medication.
 */
public class MedicationDetailDto {
    /**
     * The manufacturer of the medication.
     */
    private String fabrikant;
    /**
     * The brand name of the medication.
     */
    private String merknaam;

    // Getters and Setters
    public String getFabrikant() { return fabrikant; }
    public void setFabrikant(String fabrikant) { this.fabrikant = fabrikant; }

    public String getMerknaam() { return merknaam; }
    public void setMerknaam(String merknaam) { this.merknaam = merknaam; }
}
