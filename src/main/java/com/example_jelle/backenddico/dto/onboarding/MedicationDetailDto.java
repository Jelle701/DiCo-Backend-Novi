package com.example_jelle.backenddico.dto.onboarding;

/**
 * This class is a Data Transfer Object (DTO) for medication details.
 * It holds information about the manufacturer and brand name of a medication.
 */
public class MedicationDetailDto {
    /**
     * The manufacturer of the medication.
     */
    private String manufacturer;
    /**
     * The brand name of the medication.
     */
    private String brandName;

    // Getters and Setters
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
}
