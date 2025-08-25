package com.example_jelle.backenddico.dto.onboarding;

import com.example_jelle.backenddico.model.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * This class is a Data Transfer Object (DTO) for user preferences, typically collected during onboarding.
 * It includes validation rules for each field to ensure data integrity.
 * Double is used for numeric values to facilitate numeric validation.
 */
public class PreferencesDto {

    /**
     * The measurement system preference ("metric" or "imperial"). Cannot be blank.
     */
    @NotBlank(message = "System cannot be blank.")
    private String system;

    /**
     * The user's gender. This field is required.
     */
    @NotNull(message = "Gender is required.")
    private Gender gender;

    /**
     * The user's weight. This field is required and must be a positive number.
     */
    @NotNull(message = "Weight is required.")
    @Positive(message = "Weight must be a positive number.")
    private Double weight;

    /**
     * The user's height. This field is required and must be a positive number.
     */
    @NotNull(message = "Height is required.")
    @Positive(message = "Height must be a positive number.")
    private Double height;

    /**
     * The user's Body Mass Index (BMI). This field is required and must be a positive number.
     */
    @NotNull(message = "BMI is required.")
    @Positive(message = "BMI must be a positive number.")
    private Double bmi;

    // Getters and Setters
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
