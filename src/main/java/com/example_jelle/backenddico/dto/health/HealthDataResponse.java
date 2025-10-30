// Data Transfer Object for responding with a summary of health data.
package com.example_jelle.backenddico.dto.health;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import java.util.List;

public class HealthDataResponse {
    // A list of daily step counts for the last 7 days.
    private List<DailyStepsDto> stepsLast7Days;
    // The latest heart rate measurement.
    private DataPointDto latestHeartRate;
    // A list of glucose measurements.
    private List<GlucoseMeasurementDto> glucoseMeasurements;

    // Default constructor.
    public HealthDataResponse() {}

    // Constructs a new HealthDataResponse.
    public HealthDataResponse(List<DailyStepsDto> stepsLast7Days, DataPointDto latestHeartRate, List<GlucoseMeasurementDto> glucoseMeasurements) {
        this.stepsLast7Days = stepsLast7Days;
        this.latestHeartRate = latestHeartRate;
        this.glucoseMeasurements = glucoseMeasurements;
    }

    // Gets the list of daily step counts for the last 7 days.
    public List<DailyStepsDto> getStepsLast7Days() {
        return stepsLast7Days;
    }

    // Sets the list of daily step counts for the last 7 days.
    public void setStepsLast7Days(List<DailyStepsDto> stepsLast7Days) {
        this.stepsLast7Days = stepsLast7Days;
    }

    // Gets the latest heart rate data point.
    public DataPointDto getLatestHeartRate() {
        return latestHeartRate;
    }

    // Sets the latest heart rate data point.
    public void setLatestHeartRate(DataPointDto latestHeartRate) {
        this.latestHeartRate = latestHeartRate;
    }

    // Gets the list of glucose measurements.
    public List<GlucoseMeasurementDto> getGlucoseMeasurements() {
        return glucoseMeasurements;
    }

    // Sets the list of glucose measurements.
    public void setGlucoseMeasurements(List<GlucoseMeasurementDto> glucoseMeasurements) {
        this.glucoseMeasurements = glucoseMeasurements;
    }
}
