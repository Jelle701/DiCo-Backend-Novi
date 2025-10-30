// REST controller for managing user profiles.
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.user.LibreViewCredentialsRequest;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import com.example_jelle.backenddico.service.LibreViewAuthService;
import com.example_jelle.backenddico.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final UserService userService;
    private final UserRepository userRepository;
    private final LibreViewAuthService libreViewAuthService;

    // Constructs a new ProfileController.
    public ProfileController(UserService userService, UserRepository userRepository, LibreViewAuthService libreViewAuthService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.libreViewAuthService = libreViewAuthService;
    }

    // Retrieves the profile of the currently authenticated user.
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FullUserProfileDto> getMyProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        logger.info("GET /api/profile/me called by user: {}", userEmail);
        FullUserProfileDto userProfile = userService.findByUsernameWithAllDetails(userEmail);
        return ResponseEntity.ok(userProfile);
    }

    // Saves or updates the onboarding details for the authenticated user.
    @PutMapping({"/me", "/details"})
    @PreAuthorize("hasAnyRole('PATIENT', 'PROVIDER', 'GUARDIAN')")
    public ResponseEntity<FullUserProfileDto> saveOnboardingDetails(
            Authentication authentication,
            @Valid @RequestBody OnboardingRequestDto onboardingData) {
        String userEmail = authentication.getName();
        userService.saveProfileDetails(userEmail, onboardingData);
        FullUserProfileDto updatedProfile = userService.findByUsernameWithAllDetails(userEmail);
        return ResponseEntity.ok(updatedProfile);
    }

    // Saves LibreView credentials for the authenticated user.
    @PutMapping("/me/services/libreview")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> saveLibreViewCredentials(Authentication authentication, @Valid @RequestBody LibreViewCredentialsRequest req) {
        logger.info("Saving LibreView credentials for user: {}", authentication.getName());
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found."));

        libreViewAuthService.connectOrUpdateLibreView(user, req.getLibreViewEmail(), req.getLibreViewPassword());
        return ResponseEntity.ok().build();
    }
}
