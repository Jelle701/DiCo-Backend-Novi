package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.FullUserProfileDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
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

    /**
     * Haalt het volledige profiel op van de huidige, ingelogde gebruiker.
     * @param authentication Wordt automatisch gevuld door Spring Security.
     * @return Een ResponseEntity met de volledige profielgegevens.
     */
    @GetMapping("/me")
    public ResponseEntity<FullUserProfileDto> getMyProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        FullUserProfileDto userProfile = userService.getFullUserProfile(userEmail);
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Slaat de onboarding-gegevens van een gebruiker op.
     * @return Een ResponseEntity met de volledige, bijgewerkte profielgegevens voor consistentie.
     */
    @PutMapping("/details")
    public ResponseEntity<FullUserProfileDto> saveOnboardingDetails(
            Authentication authentication,
            @Valid @RequestBody OnboardingRequestDto onboardingData) {

        String userEmail = authentication.getName();
        FullUserProfileDto updatedUser = userService.saveProfileDetails(userEmail, onboardingData);
        return ResponseEntity.ok(updatedUser);
    }
}