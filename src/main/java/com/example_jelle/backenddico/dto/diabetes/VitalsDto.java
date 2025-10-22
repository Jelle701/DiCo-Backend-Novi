package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VitalsDto {
    private Integer systolic;
    private Integer diastolic;
    private Double weightKg;

    public VitalsDto(Integer systolic, Integer diastolic, Double weightKg) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.weightKg = weightKg;
    }

    // Getters and setters
    public Integer getSystolic() { return systolic; }
    public void setSystolic(Integer systolic) { this.systolic = systolic; }
    public Integer getDiastolic() { return diastolic; }
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
}
