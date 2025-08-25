package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.UserDto;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.payload.request.VerifyRequest;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.model.User;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines the contract for user-related operations.
 * It outlines the methods for user registration, verification, profile management, and access code handling.
 */
public interface UserService {
    /**
     * Retrieves a list of all users.
     * @return A list of UserDto objects.
     */
    List<UserDto> getUsers();

    /**
     * Finds a user by their username.
     * @param username The username to search for.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Registers a new user based on the provided registration data.
     * @param registerRequest The request object containing registration details.
     */
    void register(RegisterRequest registerRequest);

    /**
     * Verifies a user's account using a verification token.
     * @param verifyRequest The request object containing the verification token.
     */
    void verifyUser(VerifyRequest verifyRequest);

    /**
     * Saves or updates the profile details for a user.
     * @param username The username of the user to update.
     * @param onboardingRequestDto The DTO containing the new profile details.
     */
    void saveProfileDetails(String username, OnboardingRequestDto onboardingRequestDto);

    /**
     * Retrieves a comprehensive profile for a user.
     * @param username The username of the user to retrieve.
     * @return A FullUserProfileDto.
     */
    FullUserProfileDto getFullUserProfile(String username);

    /**
     * Generates a new access code for a patient.
     * @param patientEmail The email of the patient.
     * @return The generated access code.
     */
    String generateAccessCode(String patientEmail);

    /**
     * Retrieves the currently active access code for a patient.
     * @param patientEmail The email of the patient.
     * @return The active access code, or null if not found.
     */
    String getAccessCode(String patientEmail);
}
