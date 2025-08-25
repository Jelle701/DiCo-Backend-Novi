package com.example_jelle.backenddico.dto.health;

import lombok.Data;

import java.util.List;

/**
 * This class is a Data Transfer Object (DTO) for incoming health data.
 * It is used to receive batches of health data points, such as steps and heart rate, from a client.
 */
@Data
public class HealthDataRequest {
    /**
     * A list of data points representing step counts over time.
     */
    private List<DataPointDto> steps;
    /**
     * A list of data points representing heart rate measurements over time.
     */
    private List<DataPointDto> heartRate;
}
