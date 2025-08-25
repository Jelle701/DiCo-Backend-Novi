package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.health.HealthDataRequest;
import com.example_jelle.backenddico.dto.health.HealthDataResponse;
import com.example_jelle.backenddico.service.HealthDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is responsible for handling health data-related requests.
 * It provides endpoints for saving and retrieving health data for the authenticated user.
 */
@RestController
@RequestMapping("/api/health-data")
public class HealthDataController {

    private final HealthDataService healthDataService;

    public HealthDataController(HealthDataService healthDataService) {
        this.healthDataService = healthDataService;
    }

    /**
     * Saves health data (e.g., steps, heart rate) for the authenticated user.
     * @param userDetails The details of the authenticated user.
     * @param request The request body containing the health data to be saved.
     * @return A ResponseEntity with an empty body and HTTP status 200 OK on success.
     */
    @PostMapping
    public ResponseEntity<Void> saveHealthData(@AuthenticationPrincipal UserDetails userDetails, @RequestBody HealthDataRequest request) {
        healthDataService.saveHealthData(userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves a summary of health data for the authenticated user.
     * @param userDetails The details of the authenticated user.
     * @return A ResponseEntity containing the health data summary.
     */
    @GetMapping
    public ResponseEntity<HealthDataResponse> getHealthData(@AuthenticationPrincipal UserDetails userDetails) {
        HealthDataResponse response = healthDataService.getHealthDataSummary(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
