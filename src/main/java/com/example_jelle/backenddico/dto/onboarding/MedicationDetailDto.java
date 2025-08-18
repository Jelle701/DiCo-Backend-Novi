// src/main/java/com/example_jelle/backenddico/dto/onboarding/MedicationDetailDto.java
package com.example_jelle.backenddico.dto.onboarding;

public class MedicationDetailDto {
    private String fabrikant;
    private String merknaam;

    // Getters en Setters
    public String getFabrikant() { return fabrikant; }
    public void setFabrikant(String fabrikant) { this.fabrikant = fabrikant; }

    public String getMerknaam() { return merknaam; }
    public void setMerknaam(String merknaam) { this.merknaam = merknaam; }
}