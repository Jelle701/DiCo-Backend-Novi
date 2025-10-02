package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.guardian.LinkPatientRequestDto;
import com.example_jelle.backenddico.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guardian")
public class GuardianController {

    private final ProviderService providerService;

    public GuardianController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/link-patient")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<Void> linkPatient(Authentication authentication, @Valid @RequestBody LinkPatientRequestDto request) {
        String guardianUsername = authentication.getName();
        providerService.linkPatient(guardianUsername, request.getAccessCode());
        return ResponseEntity.ok().build();
    }
}
