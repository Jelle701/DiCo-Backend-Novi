package com.example_jelle.backenddico.dto.onboarding;

import com.example_jelle.backenddico.model.enums.DeviceCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * This class is a Data Transfer Object (DTO) for an individual diabetic device.
 * It is used during the onboarding process to capture details about the user's equipment.
 * All fields are mandatory.
 */
public class DeviceDto {

    /**
     * The category of the device (e.g., PUMP, SENSOR). This field is required.
     */
    @NotNull(message = "Category is required.")
    private DeviceCategory category;

    /**
     * The brand of the device. This field cannot be blank.
     */
    @NotBlank(message = "Brand cannot be blank.")
    private String brand;

    /**
     * The model name or number of the device. This field cannot be blank.
     */
    @NotBlank(message = "Model cannot be blank.")
    private String model;

    // Getters and Setters
    public DeviceCategory getCategory() {
        return category;
    }

    public void setCategory(DeviceCategory category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
