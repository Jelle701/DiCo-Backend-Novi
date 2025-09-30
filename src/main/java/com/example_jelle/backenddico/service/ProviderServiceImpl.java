package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.provider.DashboardSummaryDto;
import com.example_jelle.backenddico.dto.provider.DelegatedTokenResponseDto;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.exception.InvalidAccessException;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.AccessCode;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.AccessCodeRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import com.example_jelle.backenddico.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service implements the logic for provider-related actions.
 * It handles linking a provider to a patient and retrieving the list of linked patients.
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    private final UserRepository userRepository;
    private final AccessCodeRepository accessCodeRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public ProviderServiceImpl(UserRepository userRepository, AccessCodeRepository accessCodeRepository, UserService userService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.accessCodeRepository = accessCodeRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Links a provider to a patient using a valid access code.
     * It finds the provider, validates the access code, finds the patient,
     * and establishes the link between them.
     * @param providerUsername The username of the provider.
     * @param accessCode The access code provided by the patient.
     * @throws UserNotFoundException if the provider is not found.
     * @throws InvalidAccessException if the access code is invalid or expired.
     */
    @Override
    @Transactional
    public void linkPatient(String providerUsername, String accessCode) {
        // 1. Find the provider
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        // 2. Find the patient via the access code
        AccessCode code = accessCodeRepository.findByCodeAndExpirationTimeAfter(accessCode, LocalDateTime.now())
                .orElseThrow(() -> new InvalidAccessException("Access code is invalid or expired."));
        User patient = code.getUser();

        // 3. Link the patient to the provider and save
        provider.getLinkedPatients().add(patient);
        userRepository.save(provider);
    }

    /**
     * Retrieves a list of all patients linked to a specific provider.
     * It finds the provider and then maps their linked patient entities to DTOs.
     * @param providerUsername The username of the provider.
     * @return A list of FullUserProfileDto objects for the linked patients.
     * @throws UserNotFoundException if the provider is not found.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FullUserProfileDto> getLinkedPatients(String providerUsername) {
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        return provider.getLinkedPatients().stream()
                .map(patient -> userService.getFullUserProfile(patient.getUsername()))
                .collect(Collectors.toList());
    }

    /**
     * Generates a temporary, patient-specific token for a linked patient.
     * @param providerUsername The username of the provider requesting the token.
     * @param patientId The ID of the patient for whom to generate the delegated token.
     * @return A DelegatedTokenResponseDto containing the delegated token and patient username.
     */
    @Override
    public DelegatedTokenResponseDto generateDelegatedToken(String providerUsername, Long patientId) {
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("Patient not found with ID: " + patientId));

        if (!provider.getLinkedPatients().contains(patient)) {
            throw new InvalidAccessException("Provider is not linked to this patient.");
        }

        String delegatedToken = jwtUtil.generateDelegatedToken(patient.getUsername());
        return new DelegatedTokenResponseDto(delegatedToken, patient.getUsername());
    }

    /**
     * Retrieves an aggregated overview of all patients linked to the authenticated provider.
     * @param providerUsername The username of the provider.
     * @return A DashboardSummaryDto containing the aggregated summary.
     */
    @Override
    public DashboardSummaryDto getDashboardSummary(String providerUsername) {
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        List<User> linkedPatients = provider.getLinkedPatients().stream().collect(Collectors.toList());

        // Placeholder for actual aggregation logic
        int totalPatients = linkedPatients.size();
        int patientsWithHighGlucose = 0; // This would involve checking health data for each patient
        int patientsWithLowGlucose = 0;  // This would involve checking health data for each patient

        // In a real scenario, you would iterate through linkedPatients and fetch/aggregate their health data
        // For example:
        // for (User patient : linkedPatients) {
        //     // Fetch patient's health data and determine high/low glucose counts
        //     // This would likely involve another service, e.g., healthDataService.getGlucoseReadings(patient.getUsername())
        // }

        return new DashboardSummaryDto(totalPatients, patientsWithHighGlucose, patientsWithLowGlucose);
    }
}
