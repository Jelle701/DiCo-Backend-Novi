package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.guardian.LinkPatientRequestDto;
import com.example_jelle.backenddico.dto.provider.DelegatedTokenResponseDto;
import com.example_jelle.backenddico.dto.provider.DashboardSummaryDto;
import com.example_jelle.backenddico.dto.provider.PatientDashboardSummaryDto;
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

    /**
     * Requests a temporary, patient-specific token for a linked patient.
     * This token has limited rights (scope read:dashboard) and is used by the frontend
     * to fetch the patient's dashboard data.
     * @param authentication The authentication object for the current provider.
     * @param patientId The ID of the patient for whom to generate the delegated token.
     * @return A ResponseEntity containing the delegated token and patient username.
     */
    @PostMapping("/patients/{patientId}/delegate-token")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<DelegatedTokenResponseDto> delegateToken(
            Authentication authentication,
            @PathVariable Long patientId) {
        String providerUsername = authentication.getName();
        DelegatedTokenResponseDto response = providerService.generateDelegatedToken(providerUsername, patientId);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves an aggregated overview of all patients linked to the authenticated provider.
     * @param authentication The authentication object for the current provider.
     * @return A ResponseEntity containing a DashboardSummaryDto.
     */
    @GetMapping("/dashboard-summary")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummary(Authentication authentication) {
        String providerUsername = authentication.getName();
        DashboardSummaryDto summary = providerService.getDashboardSummary(providerUsername);
        return ResponseEntity.ok(summary);
    }

    /**
     * Retrieves a dashboard summary for a specific patient linked to the authenticated provider.
     * @param authentication The authentication object for the current provider.
     * @param patientId The ID of the patient for whom to retrieve the summary.
     * @return A ResponseEntity containing the PatientDashboardSummaryDto for the specific patient.
     */
    @GetMapping("/patients/{patientId}/dashboard-summary")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<PatientDashboardSummaryDto> getPatientDashboardSummary(
            Authentication authentication,
            @PathVariable Long patientId) {
        String providerUsername = authentication.getName();
        PatientDashboardSummaryDto summary = providerService.getPatientDashboardSummary(providerUsername, patientId);
        return ResponseEntity.ok(summary);
    }
}
