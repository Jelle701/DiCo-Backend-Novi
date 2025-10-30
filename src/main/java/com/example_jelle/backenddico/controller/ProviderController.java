// REST controller for provider-related operations.
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;
import com.example_jelle.backenddico.dto.guardian.LinkPatientRequestDto;
import com.example_jelle.backenddico.dto.provider.DashboardSummaryDto;
import com.example_jelle.backenddico.dto.provider.DelegatedTokenResponseDto;
import com.example_jelle.backenddico.dto.provider.PatientDashboardSummaryDto;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.exception.InvalidAccessException;
import com.example_jelle.backenddico.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    private final ProviderService providerService;

    // Constructs a new ProviderController.
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    // Links a provider to a patient using an access code.
    @PostMapping("/link-patient")
    @PreAuthorize("hasAnyRole('PROVIDER', 'GUARDIAN')")
    public ResponseEntity<Void> linkPatient(Authentication authentication, @Valid @RequestBody LinkPatientRequestDto request) {
        String providerUsername = authentication.getName();
        providerService.linkPatient(providerUsername, request.getAccessCode());
        return ResponseEntity.ok().build();
    }

    // Retrieves a list of patients linked to the authenticated provider.
    @GetMapping("/patients")
    @PreAuthorize("hasAnyRole('PROVIDER', 'GUARDIAN')")
    public ResponseEntity<List<FullUserProfileDto>> getLinkedPatients(Authentication authentication) {
        String providerUsername = authentication.getName();
        List<FullUserProfileDto> patients = providerService.getLinkedPatients(providerUsername);
        return ResponseEntity.ok(patients);
    }

    // Retrieves glucose measurements for a specific patient linked to the authenticated provider.
    @GetMapping("/patients/{patientId}/glucose-measurements")
    @PreAuthorize("hasAnyRole('PROVIDER', 'GUARDIAN')")
    public ResponseEntity<List<GlucoseMeasurementDto>> getPatientGlucoseMeasurements(Authentication authentication, @PathVariable Long patientId) {
        String guardianUsername = authentication.getName();
        List<GlucoseMeasurementDto> measurements = providerService.getGlucoseMeasurementsForPatient(guardianUsername, patientId);
        return ResponseEntity.ok(measurements);
    }

    // Generates a delegated token for a specific patient.
    @PostMapping("/patients/{patientId}/delegate-token")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<DelegatedTokenResponseDto> delegateToken(
            Authentication authentication,
            @PathVariable Long patientId) {
        String providerUsername = authentication.getName();
        DelegatedTokenResponseDto response = providerService.generateDelegatedToken(providerUsername, patientId);
        return ResponseEntity.ok(response);
    }

    // Retrieves an aggregated overview of all patients linked to the authenticated provider.
    @GetMapping("/dashboard-summary")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummary(Authentication authentication) {
        String providerUsername = authentication.getName();
        DashboardSummaryDto summary = providerService.getDashboardSummary(providerUsername);
        return ResponseEntity.ok(summary);
    }

    // Retrieves an aggregated overview of all patients linked to the authenticated provider.
    @GetMapping("/summary")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<DashboardSummaryDto> getProviderSummary(Authentication authentication) {
        String providerUsername = authentication.getName();
        DashboardSummaryDto summary = providerService.getDashboardSummary(providerUsername);
        return ResponseEntity.ok(summary);
    }

    // Retrieves a dashboard summary for a specific patient linked to the authenticated provider.
    @GetMapping("/patients/{patientId}/dashboard-summary")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<PatientDashboardSummaryDto> getPatientDashboardSummary(
            Authentication authentication,
            @PathVariable Long patientId) {
        String providerUsername = authentication.getName();
        PatientDashboardSummaryDto summary = providerService.getPatientDashboardSummary(providerUsername, patientId);
        return ResponseEntity.ok(summary);
    }

    // Retrieves the diabetes summary for a specific patient linked to the authenticated provider.
    @GetMapping("/patients/{patientId}/diabetes-summary")
    @PreAuthorize("hasAnyRole('PROVIDER', 'GUARDIAN')")
    public ResponseEntity<DiabetesSummaryDto> getPatientDiabetesSummary(
            Authentication authentication,
            @PathVariable Long patientId) {
        String providerUsername = authentication.getName();
        DiabetesSummaryDto summary = providerService.getDiabetesSummaryForPatient(providerUsername, patientId);
        return ResponseEntity.ok(summary);
    }

    // Handles InvalidAccessException.
    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<Map<String, String>> handleInvalidAccess(InvalidAccessException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}
