package com.example_jelle.backenddico.dto.onboarding;

import com.example_jelle.backenddico.model.enums.DiabetesType;
import com.example_jelle.backenddico.model.enums.Gender;
import com.example_jelle.backenddico.model.enums.InsulinType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * This class is a Data Transfer Object (DTO) that aggregates all the information
 * collected from a user during the onboarding process.
 * It now uses enums for specific fields and includes a list of diabetic devices.
 */
public class OnboardingRequestDto {

    private double height;
    private double weight;
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotNull(message = "Diabetes type is required.")
    private DiabetesType diabetesType;

    private InsulinType longActingInsulin;
    private InsulinType shortActingInsulin;

    @Valid // Ensures validation is cascaded to the objects in the list
    private List<DeviceDto> diabeticDevices;

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public DiabetesType getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(DiabetesType diabetesType) {
        this.diabetesType = diabetesType;
    }

    public InsulinType getLongActingInsulin() {
        return longActingInsulin;
    }

    public void setLongActingInsulin(InsulinType longActingInsulin) {
        this.longActingInsulin = longActingInsulin;
    }

    public InsulinType getShortActingInsulin() {
        return shortActingInsulin;
    }

    public void setShortActingInsulin(InsulinType shortActingInsulin) {
        this.shortActingInsulin = shortActingInsulin;
    }

    public List<DeviceDto> getDiabeticDevices() {
        return diabeticDevices;
    }

    public void setDiabeticDevices(List<DeviceDto> diabeticDevices) {
        this.diabeticDevices = diabeticDevices;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
