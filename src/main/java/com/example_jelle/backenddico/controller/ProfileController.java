package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<FullUserProfileDto> getMyProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        FullUserProfileDto userProfile = userService.getFullUserProfile(userEmail);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/details")
    public ResponseEntity<FullUserProfileDto> saveOnboardingDetails(
            Authentication authentication,
            @Valid @RequestBody OnboardingRequestDto onboardingData) {

        String userEmail = authentication.getName();
        // saveProfileDetails returns a User object
        userService.saveProfileDetails(userEmail, onboardingData);
        // We fetch the updated profile to return the DTO
        FullUserProfileDto updatedProfile = userService.getFullUserProfile(userEmail);
        return ResponseEntity.ok(updatedProfile);
    }
}