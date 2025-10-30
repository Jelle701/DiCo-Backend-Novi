// REST controller for managing medical profiles.
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.model.MedicalProfile;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.security.CustomUserDetails;
import com.example_jelle.backenddico.service.MedicalProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-profiles")
public class MedicalProfileController {

    private final MedicalProfileService service;

    // Constructs a new MedicalProfileController.
    public MedicalProfileController(MedicalProfileService service) {
        this.service = service;
    }

    // Creates or updates a medical profile for the authenticated user.
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MedicalProfile> createOrUpdate(@RequestBody MedicalProfile profile, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        profile.setUser(user);
        profile.setId(user.getId());
        return ResponseEntity.ok(service.save(profile));
    }
}
