package com.example_jelle.backenddico.dto.onboarding;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for the user's medical information.
 */
public class MedicineInfoDto {

    @NotBlank(message = "Diabetes type is verplicht.")
    private String diabetesType;

    // These fields are optional
    private String longActingInsulin;
    private String shortActingInsulin;

    // Getters and Setters
    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getLongActingInsulin() {
        return longActingInsulin;
    }

    public void setLongActingInsulin(String longActingInsulin) {
        this.longActingInsulin = longActingInsulin;
    }

    public String getShortActingInsulin() {
        return shortActingInsulin;
    }

    public void setShortActingInsulin(String shortActingInsulin) {
        this.shortActingInsulin = shortActingInsulin;
    }
}