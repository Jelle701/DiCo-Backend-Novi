// Generic Data Transfer Object for a value with a unit.
package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValueUnitDto<T> {
    private T value;
    private String unit;

    // Constructs a new ValueUnitDto.
    public ValueUnitDto(T value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    // Gets the value.
    public T getValue() {
        return value;
    }

    // Sets the value.
    public void setValue(T value) {
        this.value = value;
    }

    // Gets the unit.
    public String getUnit() {
        return unit;
    }

    // Sets the unit.
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
