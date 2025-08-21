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

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/link-patient")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> linkPatient(Authentication authentication, @Valid @RequestBody LinkPatientRequestDto request) {
        String providerUsername = authentication.getName();
        providerService.linkPatient(providerUsername, request.getAccessCode());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patients")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<FullUserProfileDto>> getLinkedPatients(Authentication authentication) {
        String providerUsername = authentication.getName();
        List<FullUserProfileDto> patients = providerService.getLinkedPatients(providerUsername);
        return ResponseEntity.ok(patients);
    }
}
