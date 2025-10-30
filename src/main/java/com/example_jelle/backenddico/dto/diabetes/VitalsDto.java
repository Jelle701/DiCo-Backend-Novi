// Data Transfer Object for vital signs.
package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VitalsDto {
    private Integer systolic;
    private Integer diastolic;
    private Double weightKg;

    // Constructs a new VitalsDto.
    public VitalsDto(Integer systolic, Integer diastolic, Double weightKg) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.weightKg = weightKg;
    }

    // Gets the systolic blood pressure.
    public Integer getSystolic() { return systolic; }
    // Sets the systolic blood pressure.
    public void setSystolic(Integer systolic) { this.systolic = systolic; }
    // Gets the diastolic blood pressure.
    public Integer getDiastolic() { return diastolic; }
    // Sets the diastolic blood pressure.
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }
    // Gets the weight in kilograms.
    public Double getWeightKg() { return weightKg; }
    // Sets the weight in kilograms.
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
}
