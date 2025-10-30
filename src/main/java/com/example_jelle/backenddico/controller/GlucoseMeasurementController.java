// REST controller for managing glucose measurements.
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import com.example_jelle.backenddico.service.GlucoseMeasurementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/glucose")
public class GlucoseMeasurementController {

    private final GlucoseMeasurementService measurementService;

    // Constructs a new GlucoseMeasurementController.
    public GlucoseMeasurementController(GlucoseMeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    // Adds a new glucose measurement for the authenticated user.
    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<GlucoseMeasurementDto> addGlucoseMeasurement(
            Authentication authentication,
            @Valid @RequestBody GlucoseMeasurementDto measurementDto) {

        String userEmail = authentication.getName();
        GlucoseMeasurementDto savedDto = measurementService.addMeasurement(userEmail, measurementDto);

        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    // Retrieves recent glucose measurements for the authenticated user.
    @GetMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<GlucoseMeasurementDto>> getRecentGlucoseMeasurements(Authentication authentication) {
        String userEmail = authentication.getName();
        List<GlucoseMeasurementDto> measurements = measurementService.getRecentMeasurements(userEmail);
        return ResponseEntity.ok(measurements);
    }
}
