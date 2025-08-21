package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.guardian.LinkPatientRequestDto;
import com.example_jelle.backenddico.service.GuardianService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guardian")
public class GuardianController {

    private final GuardianService guardianService;

    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @PostMapping("/link-patient")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<Void> linkPatient(Authentication authentication, @Valid @RequestBody LinkPatientRequestDto request) {
        String guardianUsername = authentication.getName();
        guardianService.linkPatient(guardianUsername, request.getAccessCode());
        return ResponseEntity.ok().build();
    }
}
