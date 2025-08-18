package com.example_jelle.backenddico.dto.onboarding;

import com.example_jelle.backenddico.model.enums.DeviceCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO voor een individueel diabetisch apparaat.
 * Alle velden zijn verplicht.
 */
public class DeviceDto {

    @NotNull(message = "Categorie is verplicht.")
    private DeviceCategory categorie;

    @NotBlank(message = "Fabrikant mag niet leeg zijn.")
    private String fabrikant;

    @NotBlank(message = "Model mag niet leeg zijn.")
    private String model;

    // Getters en Setters
    public DeviceCategory getCategorie() {
        return categorie;
    }

    public void setCategorie(DeviceCategory categorie) {
        this.categorie = categorie;
    }

    public String getFabrikant() {
        return fabrikant;
    }

    public void setFabrikant(String fabrikant) {
        this.fabrikant = fabrikant;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}