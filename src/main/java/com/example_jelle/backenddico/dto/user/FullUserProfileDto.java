package com.example_jelle.backenddico.dto.user;

import com.example_jelle.backenddico.dto.onboarding.DeviceDto;
import com.example_jelle.backenddico.model.Role;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserFlags;
import com.example_jelle.backenddico.model.UserProfile;
import com.example_jelle.backenddico.model.enums.DiabetesType;
import com.example_jelle.backenddico.model.enums.DeviceCategory;
import com.example_jelle.backenddico.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullUserProfileDto {

    private Long id;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private DiabetesType diabetesType;
    private Double height;
    private Double weight;
    private String longActingInsulin;
    private String shortActingInsulin;
    private List<DeviceDto> diabeticDevices;
    private UserFlags flags;
    private FullUserProfileDto linkedPatientProfile; // For GUARDIAN
    private List<FullUserProfileDto> linkedPatients; // For PROVIDER

    public FullUserProfileDto() {
    }

    public FullUserProfileDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole() != null ? user.getRole().name() : null;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateOfBirth = user.getDob();
        this.flags = user.getFlags();

        UserProfile userProfile = user.getUserProfile();
        if (userProfile != null) {
            this.gender = userProfile.getGender();
            this.diabetesType = userProfile.getDiabetesType();
            this.height = userProfile.getLength();
            this.weight = userProfile.getWeight();
            this.longActingInsulin = userProfile.getLongActingInsulin();
            this.shortActingInsulin = userProfile.getShortActingInsulin();
        }

        if (user.getUserDevices() != null) {
            this.diabeticDevices = user.getUserDevices().stream()
                    .map(device -> new DeviceDto(DeviceCategory.valueOf(device.getCategory()), device.getManufacturer(), device.getModel()))
                    .collect(Collectors.toList());
        }

        // Populate linked patient for GUARDIAN
        if (user.getRole() == Role.GUARDIAN && user.getLinkedPatient() != null) {
            this.linkedPatientProfile = new FullUserProfileDto(user.getLinkedPatient(), true);
        }

        // Populate linked patients for PROVIDER
        if (user.getRole() == Role.PROVIDER && user.getLinkedPatients() != null && !user.getLinkedPatients().isEmpty()) {
            this.linkedPatients = user.getLinkedPatients().stream()
                    .map(patient -> new FullUserProfileDto(patient, true))
                    .collect(Collectors.toList());
        }
    }

    // Overloaded constructor to prevent infinite recursion
    public FullUserProfileDto(User user, boolean isNested) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole() != null ? user.getRole().name() : null;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateOfBirth = user.getDob();
        this.flags = user.getFlags();

        UserProfile userProfile = user.getUserProfile();
        if (userProfile != null) {
            this.gender = userProfile.getGender();
            this.diabetesType = userProfile.getDiabetesType();
            this.height = userProfile.getLength();
            this.weight = userProfile.getWeight();
            this.longActingInsulin = userProfile.getLongActingInsulin();
            this.shortActingInsulin = userProfile.getShortActingInsulin();
        }

        if (user.getUserDevices() != null) {
            this.diabeticDevices = user.getUserDevices().stream()
                    .map(device -> new DeviceDto(DeviceCategory.valueOf(device.getCategory()), device.getManufacturer(), device.getModel()))
                    .collect(Collectors.toList());
        }
        // For nested DTOs, we do NOT populate the linked patient fields to avoid recursion.
    }

    // Getters and Setters for all fields, including new ones

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public DiabetesType getDiabetesType() { return diabetesType; }
    public void setDiabetesType(DiabetesType diabetesType) { this.diabetesType = diabetesType; }
    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    public String getLongActingInsulin() { return longActingInsulin; }
    public void setLongActingInsulin(String longActingInsulin) { this.longActingInsulin = longActingInsulin; }
    public String getShortActingInsulin() { return shortActingInsulin; }
    public void setShortActingInsulin(String shortActingInsulin) { this.shortActingInsulin = shortActingInsulin; }
    public List<DeviceDto> getDiabeticDevices() { return diabeticDevices; }
    public void setDiabeticDevices(List<DeviceDto> diabeticDevices) { this.diabeticDevices = diabeticDevices; }
    public UserFlags getFlags() { return flags; }
    public void setFlags(UserFlags flags) { this.flags = flags; }
    public FullUserProfileDto getLinkedPatientProfile() { return linkedPatientProfile; }
    public void setLinkedPatientProfile(FullUserProfileDto linkedPatientProfile) { this.linkedPatientProfile = linkedPatientProfile; }
    public List<FullUserProfileDto> getLinkedPatients() { return linkedPatients; }
    public void setLinkedPatients(List<FullUserProfileDto> linkedPatients) { this.linkedPatients = linkedPatients; }
}
