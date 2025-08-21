package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.UserDto;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.payload.request.VerifyRequest;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getUsers();
    Optional<User> findByUsername(String username);
    void register(RegisterRequest registerRequest);
    void verifyUser(VerifyRequest verifyRequest);
    void saveProfileDetails(String username, OnboardingRequestDto onboardingRequestDto);
    FullUserProfileDto getFullUserProfile(String username);

    // Methods for Access Code management
    String generateAccessCode(String patientEmail);
    String getAccessCode(String patientEmail);
}
