// Data Transfer Object for Postprandial Glucose (PPG) data.
package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PpgDto extends ValueUnitDto<Double> {
    private Integer atMinutes;

    // Constructs a new PpgDto.
    public PpgDto(Double value, String unit, Integer atMinutes) {
        super(value, unit);
        this.atMinutes = atMinutes;
    }

    // Gets the time in minutes.
    public Integer getAtMinutes() {
        return atMinutes;
    }

    // Sets the time in minutes.
    public void setAtMinutes(Integer atMinutes) {
        this.atMinutes = atMinutes;
    }
}
