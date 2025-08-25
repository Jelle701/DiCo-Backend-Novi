package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.guardian.LinkPatientRequestDto;
import com.example_jelle.backenddico.service.GuardianService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * This controller handles guardian-specific actions, such as linking a guardian to a patient account.
 */
@RestController
@RequestMapping("/api/guardian")
public class GuardianController {

    private final GuardianService guardianService;

    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    /**
     * Links the authenticated guardian to a patient using a patient-provided access code.
     * This endpoint is restricted to users with the 'GUARDIAN' role.
     * @param authentication The authentication object for the current guardian.
     * @param request The request body containing the patient's access code.
     * @return A ResponseEntity with an empty body and HTTP status 200 OK on success.
     */
    @PostMapping("/link-patient")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<Void> linkPatient(Authentication authentication, @Valid @RequestBody LinkPatientRequestDto request) {
        String guardianUsername = authentication.getName();
        guardianService.linkPatient(guardianUsername, request.getAccessCode());
        return ResponseEntity.ok().build();
    }
}
