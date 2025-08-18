package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.health.HealthDataRequest;
import com.example_jelle.backenddico.dto.health.HealthDataResponse;

public interface HealthDataService {
    void saveHealthData(String username, HealthDataRequest request);
    HealthDataResponse getHealthDataSummary(String username);
}
