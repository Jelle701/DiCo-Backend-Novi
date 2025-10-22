package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvgGlucoseDto {
    private String unit;
    private GlucoseValues values;

    public static class GlucoseValues {
        private Double _7d;
        private Double _14d;
        private Double _30d;
        private Double _90d;

        public GlucoseValues(Double _7d, Double _14d, Double _30d, Double _90d) {
            this._7d = _7d;
            this._14d = _14d;
            this._30d = _30d;
            this._90d = _90d;
        }

        // Getters and setters
        public Double get7d() { return _7d; }
        public void set7d(Double _7d) { this._7d = _7d; }
        public Double get14d() { return _14d; }
        public void set14d(Double _14d) { this._14d = _14d; }
        public Double get30d() { return _30d; }
        public void set30d(Double _30d) { this._30d = _30d; }
        public Double get90d() { return _90d; }
        public void set90d(Double _90d) { this._90d = _90d; }
    }

    public AvgGlucoseDto(String unit, GlucoseValues values) {
        this.unit = unit;
        this.values = values;
    }

    // Getters and setters
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public GlucoseValues getValues() { return values; }
    public void setValues(GlucoseValues values) { this.values = values; }
}
