package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.patient.AccessCodeDto;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * This controller handles patient-specific operations.
 * It provides endpoints to retrieve a patient's profile, generate a new access code,
 * and retrieve the currently active access code for the authenticated patient.
 */
@RestController
@RequestMapping("/api/patient") // Changed base path
public class PatientController {

    private final UserService userService;

    public PatientController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the full profile of a specific patient by their username.
     * @param username The username of the patient.
     * @return A ResponseEntity containing the patient's FullUserProfileDto.
     */
    // Existing endpoint, adjusted path
    @GetMapping("/{username}/profile") // Adjusted path
    public ResponseEntity<FullUserProfileDto> getPatientProfile(@PathVariable String username) {
        FullUserProfileDto userProfile = userService.getFullUserProfile(username);
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Generates a new access code for the authenticated patient.
     * This code can be used by guardians or providers to link to the patient's account.
     * @param authentication The authentication object for the current user.
     * @return A ResponseEntity containing the newly generated AccessCodeDto.
     */
    // New endpoint: Generate Access Code
    @PostMapping("/access-code/generate")
    public ResponseEntity<AccessCodeDto> generateAccessCode(Authentication authentication) {
        String patientEmail = authentication.getName();
        String newAccessCode = userService.generateAccessCode(patientEmail); // Assuming this method exists in UserService
        return ResponseEntity.ok(new AccessCodeDto(newAccessCode));
    }

    /**
     * Retrieves the currently active access code for the authenticated patient.
     * @param authentication The authentication object for the current user.
     * @return A ResponseEntity containing the active AccessCodeDto.
     * @throws ResponseStatusException if no active access code is found.
     */
    // New endpoint: Get Access Code
    @GetMapping("/access-code")
    public ResponseEntity<AccessCodeDto> getAccessCode(Authentication authentication) {
        String patientEmail = authentication.getName();
        String activeAccessCode = userService.getAccessCode(patientEmail); // Assuming this method exists in UserService
        if (activeAccessCode == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No active access code found for this patient.");
        }
        return ResponseEntity.ok(new AccessCodeDto(activeAccessCode));
    }
}
