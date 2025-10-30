// Data Transfer Object for average glucose values.
package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvgGlucoseDto {
    private String unit;
    private GlucoseValues values;

    // Represents the glucose values for different time ranges.
    public static class GlucoseValues {
        private Double _7d;
        private Double _14d;
        private Double _30d;
        private Double _90d;

        // Constructs a new GlucoseValues.
        public GlucoseValues(Double _7d, Double _14d, Double _30d, Double _90d) {
            this._7d = _7d;
            this._14d = _14d;
            this._30d = _30d;
            this._90d = _90d;
        }

        // Gets the 7-day average.
        public Double get7d() { return _7d; }
        // Sets the 7-day average.
        public void set7d(Double _7d) { this._7d = _7d; }
        // Gets the 14-day average.
        public Double get14d() { return _14d; }
        // Sets the 14-day average.
        public void set14d(Double _14d) { this._14d = _14d; }
        // Gets the 30-day average.
        public Double get30d() { return _30d; }
        // Sets the 30-day average.
        public void set30d(Double _30d) { this._30d = _30d; }
        // Gets the 90-day average.
        public Double get90d() { return _90d; }
        // Sets the 90-day average.
        public void set90d(Double _90d) { this._90d = _90d; }
    }

    // Constructs a new AvgGlucoseDto.
    public AvgGlucoseDto(String unit, GlucoseValues values) {
        this.unit = unit;
        this.values = values;
    }

    // Gets the unit.
    public String getUnit() { return unit; }
    // Sets the unit.
    public void setUnit(String unit) { this.unit = unit; }
    // Gets the glucose values.
    public GlucoseValues getValues() { return values; }
    // Sets the glucose values.
    public void setValues(GlucoseValues values) { this.values = values; }
}
