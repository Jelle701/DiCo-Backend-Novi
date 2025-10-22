package com.example_jelle.backenddico.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * This controller manages user profile data.
 * It provides endpoints for the authenticated user to retrieve their own profile
 * and to save or update their profile details (e.g., during onboarding).
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the profile of the currently authenticated user.
     * @param authentication The authentication object for the current user.
     * @return A ResponseEntity containing the user's FullUserProfileDto.
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // SECURED: Allow any authenticated user
    public ResponseEntity<FullUserProfileDto> getMyProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        logger.info("GET /api/profile/me called by user: {} with authorities: {}", userEmail, authentication.getAuthorities());
        FullUserProfileDto userProfile = userService.findByUsernameWithAllDetails(userEmail); // Use new method
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Saves or updates the profile details for the authenticated user.
     * This endpoint is secured and can only be accessed by users with the 'PATIENT', 'PROVIDER', or 'GUARDIAN' role.
     * @param authentication The authentication object for the current user.
     * @param onboardingData The DTO containing the profile details to be saved.
     * @return A ResponseEntity containing the updated FullUserProfileDto.
     */
    @PutMapping({"/me", "/details"})
    @PreAuthorize("hasAnyRole('PATIENT', 'PROVIDER', 'GUARDIAN')") // SECURED: Users with PATIENT, PROVIDER, or GUARDIAN roles can submit onboarding details.
    public ResponseEntity<FullUserProfileDto> saveOnboardingDetails(
            Authentication authentication,
            @Valid @RequestBody OnboardingRequestDto onboardingData) {

        String userEmail = authentication.getName();
        userService.saveProfileDetails(userEmail, onboardingData);
        
        // Re-fetch the user from the database to ensure the returned profile is up-to-date and fully initialized.
        FullUserProfileDto updatedProfile = userService.findByUsernameWithAllDetails(userEmail); // Use new method
        return ResponseEntity.ok(updatedProfile);
    }
}
