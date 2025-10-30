// Interface defining the contract for managing glucose measurements.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;

import java.util.List;

public interface GlucoseMeasurementService {

    // Adds a new glucose measurement for the specified user.
    GlucoseMeasurementDto addMeasurement(String userEmail, GlucoseMeasurementDto measurementDto);

    // Retrieves the glucose measurements from the last 90 days for the specified user.
    List<GlucoseMeasurementDto> getRecentMeasurements(String userEmail);
}
