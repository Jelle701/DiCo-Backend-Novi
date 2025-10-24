package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;
import com.example_jelle.backenddico.dto.guardian.LinkPatientRequestDto;
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
@RequestMapping("/guardian")
public class GuardianController {

    private final ProviderService providerService;

    public GuardianController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/link-patient")
    @PreAuthorize("hasAuthority('ROLE_GUARDIAN')")
    public ResponseEntity<Void> linkPatient(Authentication authentication, @Valid @RequestBody LinkPatientRequestDto request) {
        String guardianUsername = authentication.getName();
        providerService.linkPatient(guardianUsername, request.getAccessCode());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/linked-patients")
    @PreAuthorize("hasAuthority('ROLE_GUARDIAN')")
    public ResponseEntity<List<FullUserProfileDto>> getLinkedPatients(Authentication authentication) {
        String guardianUsername = authentication.getName();
        List<FullUserProfileDto> linkedPatients = providerService.getLinkedPatients(guardianUsername);
        return ResponseEntity.ok(linkedPatients);
    }

    @GetMapping("/linked-patients/{patientId}/glucose-measurements")
    @PreAuthorize("hasAuthority('ROLE_GUARDIAN')")
    public ResponseEntity<List<GlucoseMeasurementDto>> getPatientGlucoseMeasurements(Authentication authentication, @PathVariable Long patientId) {
        String guardianUsername = authentication.getName();
        List<GlucoseMeasurementDto> measurements = providerService.getGlucoseMeasurementsForPatient(guardianUsername, patientId);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/linked-patients/{patientId}/summary-report")
    @PreAuthorize("hasAuthority('ROLE_GUARDIAN')")
    public ResponseEntity<DiabetesSummaryDto> getPatientDiabetesSummary(
            Authentication authentication,
            @PathVariable Long patientId) {
        String guardianUsername = authentication.getName();
        DiabetesSummaryDto summary = providerService.getDiabetesSummaryForPatient(guardianUsername, patientId);
        return ResponseEntity.ok(summary);
    }

    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<Map<String, String>> handleInvalidAccess(InvalidAccessException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}
