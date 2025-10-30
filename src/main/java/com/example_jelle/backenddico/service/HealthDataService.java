// Interface for services related to health data.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.health.HealthDataRequest;
import com.example_jelle.backenddico.dto.health.HealthDataResponse;

public interface HealthDataService {
    // Saves health data for a user.
    void saveHealthData(String username, HealthDataRequest request);
    // Retrieves a summary of health data for a user.
    HealthDataResponse getHealthDataSummary(String username);
}
