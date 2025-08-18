package com.example_jelle.backenddico.dto;

import com.example_jelle.backenddico.dto.onboarding.DeviceDto;
import com.example_jelle.backenddico.dto.onboarding.PreferencesDto;
import com.example_jelle.backenddico.dto.onboarding.MedicineInfoDto;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserProfile;
import com.example_jelle.backenddico.model.enums.DeviceCategory;
import com.example_jelle.backenddico.model.enums.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Een uitgebreide DTO die de volledige profielinformatie van een gebruiker bevat,
 * inclusief geneste objecten voor voorkeuren en apparaten.
 * Dit is de standaard response voor GET /api/profile/me en PUT /api/profile/details.
 */
public class FullUserProfileDto {

    private static final Logger logger = LoggerFactory.getLogger(FullUserProfileDto.class);

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String role;
    private PreferencesDto preferences;
    private MedicineInfoDto medicineInfo;
    private List<DeviceDto> diabeticDevices;
    private UserFlagsDto flags;

    // Getters en Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public PreferencesDto getPreferences() { return preferences; }
    public void setPreferences(PreferencesDto preferences) { this.preferences = preferences; }
    public MedicineInfoDto getMedicineInfo() { return medicineInfo; }
    public void setMedicineInfo(MedicineInfoDto medicineInfo) { this.medicineInfo = medicineInfo; }
    public List<DeviceDto> getDiabeticDevices() { return diabeticDevices; }
    public void setDiabeticDevices(List<DeviceDto> diabeticDevices) { this.diabeticDevices = diabeticDevices; }
    public UserFlagsDto getFlags() { return flags; }
    public void setFlags(UserFlagsDto flags) { this.flags = flags; }

    /**
     * Statische factory-methode om een FullUserProfileDto te creëren op basis van de User entiteit
     * en de gerelateerde profiel- en apparaatgegevens.
     */
    public static FullUserProfileDto from(User user) {
        FullUserProfileDto dto = new FullUserProfileDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDob(user.getDob());
        dto.setRole(user.getRole());
        dto.setFlags(UserFlagsDto.from(user.getFlags()));

        // Map UserProfile naar PreferencesDto
        if (user.getUserProfile() != null) {
            UserProfile profile = user.getUserProfile();
            PreferencesDto prefsDto = new PreferencesDto();
            prefsDto.setSystem(profile.getMeasurementSystem());

            if (profile.getGender() != null) {
                try {
                    prefsDto.setGender(Gender.valueOf(profile.getGender().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    logger.warn("Could not parse gender '{}' for user {}", profile.getGender(), user.getId());
                }
            }
            prefsDto.setWeight(profile.getWeight());
            prefsDto.setHeight(profile.getHeight());
            prefsDto.setBmi(profile.getBmi());
            dto.setPreferences(prefsDto);

            // Map UserProfile medicine info naar MedicineInfoDto, indien aanwezig
            if (profile.getDiabetesType() != null && !profile.getDiabetesType().isBlank()) {
                MedicineInfoDto medDto = new MedicineInfoDto();
                medDto.setDiabetesType(profile.getDiabetesType());
                medDto.setLongActingInsulin(profile.getLongActingInsulin());
                medDto.setShortActingInsulin(profile.getShortActingInsulin());
                dto.setMedicineInfo(medDto);
            }
        }

        // Map UserDevice lijst naar DeviceDto lijst
        if (user.getDevices() != null) {
            dto.setDiabeticDevices(user.getDevices().stream().map(device -> {
                DeviceDto deviceDto = new DeviceDto();
                if (device.getCategory() != null) {
                    try {
                        deviceDto.setCategorie(DeviceCategory.valueOf(device.getCategory().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        logger.warn("Could not parse device category '{}' for user {}", device.getCategory(), user.getId());
                    }
                }
                deviceDto.setFabrikant(device.getManufacturer());
                deviceDto.setModel(device.getModel());
                return deviceDto;
            }).collect(Collectors.toList()));
        } else {
            dto.setDiabeticDevices(Collections.emptyList());
        }

        return dto;
    }
}