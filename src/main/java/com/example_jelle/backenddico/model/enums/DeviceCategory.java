package com.example_jelle.backenddico.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum voor de verschillende apparaatcategorieën.
 * De @JsonProperty annotatie zorgt ervoor dat de JSON-string (lowercase)
 * correct wordt omgezet naar de Java Enum (UPPERCASE).
 */
public enum DeviceCategory {
    @JsonProperty("pomp")
    POMP,
    @JsonProperty("cgm")
    CGM,
    @JsonProperty("meter")
    METER
}