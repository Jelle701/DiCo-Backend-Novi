// Data Transfer Object for a summary of diabetes-related data.
package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiabetesSummaryDto {
    private ValueUnitDto<Double> hbA1c;
    private AvgGlucoseDto avgGlucose;
    private TimeInRangeDto timeInRange;
    private Double cvPct;
    private ValueUnitDto<Double> fpg;
    private PpgDto ppg;
    private LifestyleDto lifestyle;
    private VitalsDto vitals;
    private Instant updatedAt;

    // Gets the HbA1c value.
    public ValueUnitDto<Double> getHbA1c() { return hbA1c; }
    // Sets the HbA1c value.
    public void setHbA1c(ValueUnitDto<Double> hbA1c) { this.hbA1c = hbA1c; }
    // Gets the average glucose data.
    public AvgGlucoseDto getAvgGlucose() { return avgGlucose; }
    // Sets the average glucose data.
    public void setAvgGlucose(AvgGlucoseDto avgGlucose) { this.avgGlucose = avgGlucose; }
    // Gets the time in range data.
    public TimeInRangeDto getTimeInRange() { return timeInRange; }
    // Sets the time in range data.
    public void setTimeInRange(TimeInRangeDto timeInRange) { this.timeInRange = timeInRange; }
    // Gets the coefficient of variation percentage.
    public Double getCvPct() { return cvPct; }
    // Sets the coefficient of variation percentage.
    public void setCvPct(Double cvPct) { this.cvPct = cvPct; }
    // Gets the fasting plasma glucose (FPG) value.
    public ValueUnitDto<Double> getFpg() { return fpg; }
    // Sets the fasting plasma glucose (FPG) value.
    public void setFpg(ValueUnitDto<Double> fpg) { this.fpg = fpg; }
    // Gets the postprandial glucose (PPG) data.
    public PpgDto getPpg() { return ppg; }
    // Sets the postprandial glucose (PPG) data.
    public void setPpg(PpgDto ppg) { this.ppg = ppg; }
    // Gets the lifestyle data.
    public LifestyleDto getLifestyle() { return lifestyle; }
    // Sets the lifestyle data.
    public void setLifestyle(LifestyleDto lifestyle) { this.lifestyle = lifestyle; }
    // Gets the vitals data.
    public VitalsDto getVitals() { return vitals; }
    // Sets the vitals data.
    public void setVitals(VitalsDto vitals) { this.vitals = vitals; }
    // Gets the last updated timestamp.
    public Instant getUpdatedAt() { return updatedAt; }
    // Sets the last updated timestamp.
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
