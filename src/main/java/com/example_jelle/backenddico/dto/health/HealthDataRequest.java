// Data Transfer Object for incoming health data.
package com.example_jelle.backenddico.dto.health;

import java.util.List;

public class HealthDataRequest {
    // A list of data points representing step counts over time.
    private List<DataPointDto> steps;
    // A list of data points representing heart rate measurements over time.
    private List<DataPointDto> heartRate;

    // Gets the list of step data points.
    public List<DataPointDto> getSteps() {
        return steps;
    }

    // Sets the list of step data points.
    public void setSteps(List<DataPointDto> steps) {
        this.steps = steps;
    }

    // Gets the list of heart rate data points.
    public List<DataPointDto> getHeartRate() {
        return heartRate;
    }

    // Sets the list of heart rate data points.
    public void setHeartRate(List<DataPointDto> heartRate) {
        this.heartRate = heartRate;
    }
}
