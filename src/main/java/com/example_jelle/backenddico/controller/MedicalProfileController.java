package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.model.MedicalProfile;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.security.CustomUserDetails;
import com.example_jelle.backenddico.service.MedicalProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * This controller handles the creation of medical profiles.
 */
@RestController
@RequestMapping("/api/medical-profiles") // Changed to /api prefix for consistency
public class MedicalProfileController {

    private final MedicalProfileService service;

    public MedicalProfileController(MedicalProfileService service) {
        this.service = service;
    }

    /**
     * Creates or updates the medical profile for the authenticated user.
     * @param authentication The authentication object for the current user.
     * @param profile The medical profile data to save.
     * @return A ResponseEntity containing the created medical profile.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()") // SECURED: Only authenticated users can create/update their profile.
    public ResponseEntity<MedicalProfile> createOrUpdate(@RequestBody MedicalProfile profile, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        profile.setUser(user); // Ensure the profile is linked to the authenticated user.
        profile.setId(user.getId()); // Ensure the ID matches the user's ID.
        return ResponseEntity.ok(service.save(profile));
    }
}
