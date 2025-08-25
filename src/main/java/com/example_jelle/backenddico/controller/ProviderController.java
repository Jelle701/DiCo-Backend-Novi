package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.guardian.LinkPatientRequestDto;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller handles provider-specific actions, such as linking to patient accounts
 * and viewing linked patients. All endpoints are restricted to users with the 'PROVIDER' role.
 */
@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * Links the authenticated provider to a patient using a patient-provided access code.
     * @param authentication The authentication object for the current provider.
     * @param request The request body containing the patient's access code.
     * @return A ResponseEntity with an empty body and HTTP status 200 OK on success.
     */
    @PostMapping("/link-patient")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> linkPatient(Authentication authentication, @Valid @RequestBody LinkPatientRequestDto request) {
        String providerUsername = authentication.getName();
        providerService.linkPatient(providerUsername, request.getAccessCode());
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves a list of all patients linked to the authenticated provider.
     * @param authentication The authentication object for the current provider.
     * @return A ResponseEntity containing a list of FullUserProfileDto for the linked patients.
     */
    @GetMapping("/patients")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<FullUserProfileDto>> getLinkedPatients(Authentication authentication) {
        String providerUsername = authentication.getName();
        List<FullUserProfileDto> patients = providerService.getLinkedPatients(providerUsername);
        return ResponseEntity.ok(patients);
    }
}
