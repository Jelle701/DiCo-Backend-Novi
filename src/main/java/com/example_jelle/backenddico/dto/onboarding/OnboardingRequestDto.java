package com.example_jelle.backenddico.dto.onboarding;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * This class is a Data Transfer Object (DTO) that aggregates all the information
 * collected from a user during the onboarding process.
 */
public class OnboardingRequestDto {

    private double height;
    private double weight;
    private LocalDate dateOfBirth;
    private String gender;
    private String diabetesType;
    private String medication;

    /**
     * The role selected by the user (e.g., PATIENT, GUARDIAN, PROVIDER).
     * This field is required.
     */
    @NotBlank(message = "Role is required.")
    private String role;

    // Getters and Setters
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
