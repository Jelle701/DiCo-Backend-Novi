// Interface defining the contract for user-related operations.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.AdminUpdateUserDto;
import com.example_jelle.backenddico.dto.AdminUserDto;
import com.example_jelle.backenddico.dto.ServiceStatusDto;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.UserDto;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.payload.request.VerifyRequest;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // Retrieves a list of all users.
    List<UserDto> getUsers();

    // Finds a user by their username.
    Optional<User> findByUsername(String username);

    // Registers a new user.
    void register(RegisterRequest registerRequest);

    // Verifies a user's account using a verification token.
    void verifyUser(VerifyRequest verifyRequest);

    // Saves or updates the profile details for a user.
    void saveProfileDetails(String username, OnboardingRequestDto onboardingRequestDto);

    // Retrieves a comprehensive profile for a user.
    FullUserProfileDto getFullUserProfile(String username);

    // Generates a new access code for a patient.
    String generateAccessCode(String patientEmail);

    // Retrieves the currently active access code for a patient.
    String getAccessCode(String patientEmail);

    // Deletes a user and all associated data.
    void deleteUser(String username);

    // Retrieves a list of all users formatted for the admin dashboard.
    List<AdminUserDto> getAllUsersForAdmin();

    // Deletes a user by their ID, for admin purposes.
    void deleteUserById(Long userId);

    // Updates a user's details as an admin.
    AdminUserDto updateUserAsAdmin(Long userId, AdminUpdateUserDto updateUserDto);

    // Retrieves the status of all external service connections for a user.
    List<ServiceStatusDto> getServiceConnectionStatuses(User user);

    // Retrieves a comprehensive profile for a user, ensuring all lazy-loaded collections are fetched.
    FullUserProfileDto findByUsernameWithAllDetails(String username);
}
