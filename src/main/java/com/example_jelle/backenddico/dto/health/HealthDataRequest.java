package com.example_jelle.backenddico.dto.health;

// import lombok.Data; // Tijdelijk verwijderd

import java.util.List;

/**
 * This class is a Data Transfer Object (DTO) for incoming health data.
 * It is used to receive batches of health data points, such as steps and heart rate, from a client.
 */
// @Data // Tijdelijk verwijderd
public class HealthDataRequest {
    /**
     * A list of data points representing step counts over time.
     */
    private List<DataPointDto> steps;
    /**
     * A list of data points representing heart rate measurements over time.
     */
    private List<DataPointDto> heartRate;

    // Handmatige getters en setters
    public List<DataPointDto> getSteps() {
        return steps;
    }

    public void setSteps(List<DataPointDto> steps) {
        this.steps = steps;
    }

    public List<DataPointDto> getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(List<DataPointDto> heartRate) {
        this.heartRate = heartRate;
    }
}
