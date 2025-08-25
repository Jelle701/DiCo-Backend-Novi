package com.example_jelle.backenddico.dto.onboarding;

import jakarta.validation.constraints.NotBlank;

/**
 * This class is a Data Transfer Object (DTO) for the user's medical information,
 * specifically related to their diabetes management.
 */
public class MedicineInfoDto {

    /**
     * The type of diabetes the user has. This field is required.
     */
    @NotBlank(message = "Diabetes type is required.")
    private String diabetesType;

    /**
     * The type of long-acting insulin the user takes. This field is optional.
     */
    private String longActingInsulin;

    /**
     * The type of short-acting insulin the user takes. This field is optional.
     */
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
