package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;
import com.example_jelle.backenddico.exception.DataNotFoundException;
import com.example_jelle.backenddico.service.DiabetesService;
import com.example_jelle.backenddico.service.ProviderService; // Import ProviderService
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/diabetes")
public class DiabetesController {

    private final DiabetesService diabetesService;
    private final ProviderService providerService; // Inject ProviderService

    public DiabetesController(DiabetesService diabetesService, ProviderService providerService) {
        this.diabetesService = diabetesService;
        this.providerService = providerService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DiabetesSummaryDto> getDiabetesSummary(Authentication authentication) {
        String username = authentication.getName();
        DiabetesSummaryDto summary = diabetesService.getDiabetesSummary(username);
        return ResponseEntity.ok(summary);
    }

    /**
     * Endpoint to retrieve the diabetes summary for a specific patient.
     * Accessible by the patient themselves, or by a linked provider/guardian.
     *
     * @param patientId The ID of the patient.
     * @param authentication The current authentication object.
     * @return A response entity containing the patient's diabetes summary data.
     */
    @GetMapping("/summary/patient/{patientId}")
    @PreAuthorize("hasRole('PATIENT') and #patientId == authentication.principal.id or hasRole('PROVIDER') or hasRole('GUARDIAN')")
    public ResponseEntity<DiabetesSummaryDto> getDiabetesSummaryForPatient(
            @PathVariable Long patientId,
            Authentication authentication) {
        String requestingUsername = authentication.getName();
        DiabetesSummaryDto summary = providerService.getDiabetesSummaryForPatient(requestingUsername, patientId);
        return ResponseEntity.ok(summary);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDataNotFoundException(DataNotFoundException ex) {
        return new ResponseEntity<>(Map.of("message", "Geen samenvattingsgegevens beschikbaar voor deze gebruiker."), HttpStatus.NOT_FOUND);
    }
}
