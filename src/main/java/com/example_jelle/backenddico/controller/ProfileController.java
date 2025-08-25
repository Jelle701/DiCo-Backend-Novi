package com.example_jelle.backenddico.controller;

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
    public ResponseEntity<FullUserProfileDto> getMyProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        FullUserProfileDto userProfile = userService.getFullUserProfile(userEmail);
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Saves or updates the profile details for the authenticated user.
     * This endpoint is secured and can only be accessed by users with the 'PATIENT' role.
     * @param authentication The authentication object for the current user.
     * @param onboardingData The DTO containing the profile details to be saved.
     * @return A ResponseEntity containing the updated FullUserProfileDto.
     */
    @PutMapping("/details")
    @PreAuthorize("hasRole('PATIENT')") // SECURED: Only users with the default PATIENT role can submit onboarding details.
    public ResponseEntity<FullUserProfileDto> saveOnboardingDetails(
            Authentication authentication,
            @Valid @RequestBody OnboardingRequestDto onboardingData) {

        String userEmail = authentication.getName();
        userService.saveProfileDetails(userEmail, onboardingData);
        FullUserProfileDto updatedProfile = userService.getFullUserProfile(userEmail);
        return ResponseEntity.ok(updatedProfile);
    }
}
