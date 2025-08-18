package com.example_jelle.backenddico.dto.onboarding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * De hoofd-DTO die alle data van de PUT /api/profile/details request bevat.
 * De @Valid annotaties zorgen ervoor dat de validatieregels in de geneste
 * DTO's (PreferencesDto en DeviceDto) ook worden gecontroleerd.
 */
public class OnboardingRequestDto {

    @NotNull(message = "Het 'preferences' object mag niet ontbreken.")
    @Valid // Zorgt ervoor dat de velden BINNEN PreferencesDto worden gevalideerd.
    private PreferencesDto preferences;

    @Valid // Zorgt ervoor dat elk object in de lijst wordt gevalideerd.
    private List<DeviceDto> diabeticDevices;

    @Valid // Valideer het medicineInfo object als het aanwezig is.
    private MedicineInfoDto medicineInfo;

    // Getters en Setters
    public PreferencesDto getPreferences() {
        return preferences;
    }

    public void setPreferences(PreferencesDto preferences) {
        this.preferences = preferences;
    }

    public List<DeviceDto> getDiabeticDevices() {
        return diabeticDevices;
    }

    public void setDiabeticDevices(List<DeviceDto> diabeticDevices) {
        this.diabeticDevices = diabeticDevices;
    }

    public MedicineInfoDto getMedicineInfo() {
        return medicineInfo;
    }

    public void setMedicineInfo(MedicineInfoDto medicineInfo) {
        this.medicineInfo = medicineInfo;
    }
}