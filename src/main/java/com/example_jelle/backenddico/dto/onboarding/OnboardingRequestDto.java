package com.example_jelle.backenddico.dto.onboarding;

import com.example_jelle.backenddico.model.enums.DiabetesType;
import com.example_jelle.backenddico.model.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

/**
 * This class is a Data Transfer Object (DTO) that aggregates all the information
 * collected from a user during the onboarding process.
 * It now uses enums for specific fields and includes a list of diabetic devices.
 */
public class OnboardingRequestDto {

    private String firstName;
    private String lastName;
    private double height;
    private double weight;
    private LocalDate dateOfBirth;

    private Gender gender;

    private DiabetesType diabetesType;

    private String longActingInsulin;
    private String shortActingInsulin;

    @Valid // Ensures validation is cascaded to the objects in the list
    private List<DeviceDto> diabeticDevices;

    @NotBlank(message = "Role is required.")
    private String role;

    // Getters and Setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
