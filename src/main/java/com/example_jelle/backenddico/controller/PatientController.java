package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.patient.AccessCodeDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final UserService userService;

    public PatientController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}/profile")
    @PreAuthorize("hasRole('PATIENT') or hasRole('PROVIDER') or hasRole('GUARDIAN')")
    public ResponseEntity<FullUserProfileDto> getPatientProfile(@PathVariable String username) {
        FullUserProfileDto userProfile = userService.getFullUserProfile(username);
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping("/access-code/generate")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AccessCodeDto> generateAccessCode(Authentication authentication) {
        String patientEmail = authentication.getName();
        String newAccessCode = userService.generateAccessCode(patientEmail);
        return ResponseEntity.ok(new AccessCodeDto(newAccessCode));
    }

    @GetMapping("/access-code")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AccessCodeDto> getAccessCode(Authentication authentication) {
        String patientEmail = authentication.getName();
        String activeAccessCode = userService.getAccessCode(patientEmail);
        if (activeAccessCode == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new AccessCodeDto(activeAccessCode));
    }
}
