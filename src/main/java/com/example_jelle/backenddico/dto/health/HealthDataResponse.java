package com.example_jelle.backenddico.dto.health;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
// import lombok.AllArgsConstructor; // Tijdelijk verwijderd
// import lombok.Data; // Tijdelijk verwijderd
// import lombok.NoArgsConstructor; // Tijdelijk verwijderd

import java.util.List;

/**
 * This class is a Data Transfer Object (DTO) for responding with a summary of health data.
 * It provides a curated view of a user's recent health metrics.
 */
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class HealthDataResponse {
    private List<DailyStepsDto> stepsLast7Days;
    private DataPointDto latestHeartRate;
    private List<GlucoseMeasurementDto> glucoseMeasurements;

    // Handmatige No-Args Constructor
    public HealthDataResponse() {}

    // Handmatige All-Args Constructor (vereist door de service)
    public HealthDataResponse(List<DailyStepsDto> stepsLast7Days, DataPointDto latestHeartRate, List<GlucoseMeasurementDto> glucoseMeasurements) {
        this.stepsLast7Days = stepsLast7Days;
        this.latestHeartRate = latestHeartRate;
        this.glucoseMeasurements = glucoseMeasurements;
    }

    // Handmatige getters en setters
    public List<DailyStepsDto> getStepsLast7Days() {
        return stepsLast7Days;
    }

    public void setStepsLast7Days(List<DailyStepsDto> stepsLast7Days) {
        this.stepsLast7Days = stepsLast7Days;
    }

    public DataPointDto getLatestHeartRate() {
        return latestHeartRate;
    }

    public void setLatestHeartRate(DataPointDto latestHeartRate) {
        this.latestHeartRate = latestHeartRate;
    }

    public List<GlucoseMeasurementDto> getGlucoseMeasurements() {
        return glucoseMeasurements;
    }

    public void setGlucoseMeasurements(List<GlucoseMeasurementDto> glucoseMeasurements) {
        this.glucoseMeasurements = glucoseMeasurements;
    }
}
