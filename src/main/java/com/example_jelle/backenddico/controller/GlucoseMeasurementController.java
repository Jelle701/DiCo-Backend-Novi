package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import com.example_jelle.backenddico.service.GlucoseMeasurementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Belangrijke import
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller handles operations related to glucose measurements.
 * It provides endpoints for adding new measurements and retrieving recent measurements
 * for the authenticated user. Access is restricted to authenticated users.
 */
@RestController
@RequestMapping("/api/glucose")
public class GlucoseMeasurementController {

    private final GlucoseMeasurementService measurementService;

    public GlucoseMeasurementController(GlucoseMeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    /**
     * Adds a new glucose measurement for the authenticated user.
     * The user must have the 'USER' role.
     * @param authentication The authentication object for the current user.
     * @param measurementDto The DTO containing the details of the glucose measurement.
     * @return A ResponseEntity containing the saved GlucoseMeasurementDto with HTTP status 201 CREATED.
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')") // FIX: Aangepast van 'ADMIN' naar 'USER'
    public ResponseEntity<GlucoseMeasurementDto> addGlucoseMeasurement(
            Authentication authentication,
            @Valid @RequestBody GlucoseMeasurementDto measurementDto) {

        String userEmail = authentication.getName();
        GlucoseMeasurementDto savedDto = measurementService.addMeasurement(userEmail, measurementDto);

        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    /**
     * Retrieves recent glucose measurements for the authenticated user.
     * The user must have the 'USER' role.
     * @param authentication The authentication object for the current user.
     * @return A ResponseEntity containing a list of recent GlucoseMeasurementDto objects.
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')") // Consistentie: ook hier de juiste rol vereisen
    public ResponseEntity<List<GlucoseMeasurementDto>> getRecentGlucoseMeasurements(Authentication authentication) {
        String userEmail = authentication.getName();
        List<GlucoseMeasurementDto> measurements = measurementService.getRecentMeasurements(userEmail);
        return ResponseEntity.ok(measurements);
    }
}
