package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;

import java.util.List;

/**
 * This interface defines the contract for managing glucose measurements.
 * It provides methods for adding new measurements and retrieving recent ones.
 */
public interface GlucoseMeasurementService {

    /**
     * Adds a new glucose measurement for the specified user.
     *
     * @param userEmail The email of the logged-in user.
     * @param measurementDto The DTO containing the measurement data.
     * @return The saved measurement as a DTO.
     */
    GlucoseMeasurementDto addMeasurement(String userEmail, GlucoseMeasurementDto measurementDto);

    /**
     * Retrieves the glucose measurements from the last 90 days for the specified user.
     *
     * @param userEmail The email of the logged-in user.
     * @return A list of recent measurements.
     */
    List<GlucoseMeasurementDto> getRecentMeasurements(String userEmail);
}
