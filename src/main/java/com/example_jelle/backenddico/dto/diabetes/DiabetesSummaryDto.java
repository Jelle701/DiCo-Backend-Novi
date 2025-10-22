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

    // Getters and setters
    public ValueUnitDto<Double> getHbA1c() { return hbA1c; }
    public void setHbA1c(ValueUnitDto<Double> hbA1c) { this.hbA1c = hbA1c; }
    public AvgGlucoseDto getAvgGlucose() { return avgGlucose; }
    public void setAvgGlucose(AvgGlucoseDto avgGlucose) { this.avgGlucose = avgGlucose; }
    public TimeInRangeDto getTimeInRange() { return timeInRange; }
    public void setTimeInRange(TimeInRangeDto timeInRange) { this.timeInRange = timeInRange; }
    public Double getCvPct() { return cvPct; }
    public void setCvPct(Double cvPct) { this.cvPct = cvPct; }
    public ValueUnitDto<Double> getFpg() { return fpg; }
    public void setFpg(ValueUnitDto<Double> fpg) { this.fpg = fpg; }
    public PpgDto getPpg() { return ppg; }
    public void setPpg(PpgDto ppg) { this.ppg = ppg; }
    public LifestyleDto getLifestyle() { return lifestyle; }
    public void setLifestyle(LifestyleDto lifestyle) { this.lifestyle = lifestyle; }
    public VitalsDto getVitals() { return vitals; }
    public void setVitals(VitalsDto vitals) { this.vitals = vitals; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
