package com.example_jelle.backenddico.dto.health;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class is a Data Transfer Object (DTO) for responding with a summary of health data.
 * It provides a curated view of a user's recent health metrics, such as daily steps for the last week
 * and the most recent heart rate measurement, and now includes glucose measurements.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDataResponse {
    /**
     * A list of daily step counts for the last 7 days.
     */
    private List<DailyStepsDto> stepsLast7Days;
    /**
     * The most recently recorded heart rate data point.
     */
    private DataPointDto latestHeartRate;
    /**
     * A list of glucose measurements.
     */
    private List<GlucoseMeasurementDto> glucoseMeasurements;
    // Added comment to force recompile
}
