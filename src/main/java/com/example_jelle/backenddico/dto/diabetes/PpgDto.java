package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PpgDto extends ValueUnitDto<Double> {
    private Integer atMinutes;

    public PpgDto(Double value, String unit, Integer atMinutes) {
        super(value, unit);
        this.atMinutes = atMinutes;
    }

    public Integer getAtMinutes() {
        return atMinutes;
    }

    public void setAtMinutes(Integer atMinutes) {
        this.atMinutes = atMinutes;
    }
}
