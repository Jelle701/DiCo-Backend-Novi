package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.health.HealthDataRequest;
import com.example_jelle.backenddico.dto.health.HealthDataResponse;
import com.example_jelle.backenddico.service.HealthDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-data")
public class HealthDataController {

    private final HealthDataService healthDataService;

    public HealthDataController(HealthDataService healthDataService) {
        this.healthDataService = healthDataService;
    }

    @PostMapping
    public ResponseEntity<Void> saveHealthData(@AuthenticationPrincipal UserDetails userDetails, @RequestBody HealthDataRequest request) {
        healthDataService.saveHealthData(userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<HealthDataResponse> getHealthData(@AuthenticationPrincipal UserDetails userDetails) {
        HealthDataResponse response = healthDataService.getHealthDataSummary(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
