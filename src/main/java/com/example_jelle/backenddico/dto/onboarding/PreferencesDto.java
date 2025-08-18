package com.example_jelle.backenddico.dto.onboarding;

import com.example_jelle.backenddico.model.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO voor de gebruikersvoorkeuren. Bevat validatieregels voor elk veld.
 * Gebruikt Double voor numerieke waarden om getalvalidatie mogelijk te maken.
 */
public class PreferencesDto {

    @NotBlank(message = "Systeem (system) mag niet leeg zijn.")
    private String system; // "metric" of "imperial"

    @NotNull(message = "Geslacht (gender) is verplicht.")
    private Gender gender;

    @NotNull(message = "Gewicht (weight) is verplicht.")
    @Positive(message = "Gewicht (weight) moet een positief getal zijn.")
    private Double weight;

    @NotNull(message = "Lengte (height) is verplicht.")
    @Positive(message = "Lengte (height) moet een positief getal zijn.")
    private Double height;

    @NotNull(message = "BMI is verplicht.")
    @Positive(message = "BMI moet een positief getal zijn.")
    private Double bmi;

    // Getters en Setters
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }
}