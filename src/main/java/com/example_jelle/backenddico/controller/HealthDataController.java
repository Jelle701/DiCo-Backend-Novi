package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.health.HealthDataRequest;
import com.example_jelle.backenddico.dto.health.HealthDataResponse;
import com.example_jelle.backenddico.service.HealthDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health-data")
public class HealthDataController {

    private final HealthDataService healthDataService;

    public HealthDataController(HealthDataService healthDataService) {
        this.healthDataService = healthDataService;
    }

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Void> saveHealthData(@AuthenticationPrincipal UserDetails userDetails, @RequestBody HealthDataRequest request) {
        healthDataService.saveHealthData(userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('PATIENT') or hasAuthority('SCOPE_read:dashboard')")
    public ResponseEntity<HealthDataResponse> getHealthData(@AuthenticationPrincipal UserDetails userDetails) {
        HealthDataResponse response = healthDataService.getHealthDataSummary(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
