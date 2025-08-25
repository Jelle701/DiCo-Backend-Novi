package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.exception.InvalidAccessException;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.AccessCode;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.AccessCodeRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * This service implements the logic for guardian-related actions, specifically for linking a guardian to a patient.
 */
@Service
public class GuardianServiceImpl implements GuardianService {

    private final UserRepository userRepository;
    private final AccessCodeRepository accessCodeRepository;

    public GuardianServiceImpl(UserRepository userRepository, AccessCodeRepository accessCodeRepository) {
        this.userRepository = userRepository;
        this.accessCodeRepository = accessCodeRepository;
    }

    /**
     * Links a guardian to a patient using a valid access code.
     * It performs several checks: finds the guardian, ensures they are not already linked,
     * validates the access code, and then establishes the link.
     * @param guardianUsername The username of the guardian.
     * @param accessCode The access code provided by the patient.
     * @throws UserNotFoundException if the guardian is not found.
     * @throws InvalidAccessException if the guardian is already linked or the access code is invalid/expired.
     */
    @Override
    @Transactional
    public void linkPatient(String guardianUsername, String accessCode) {
        // 1. Find the guardian
        User guardian = userRepository.findByUsername(guardianUsername)
                .orElseThrow(() -> new UserNotFoundException("Guardian not found: " + guardianUsername));

        // 2. Validate that the guardian is not already linked
        if (guardian.getLinkedPatient() != null) {
            throw new InvalidAccessException("Guardian is already linked to a patient.");
        }

        // 3. Find the patient via the access code
        AccessCode code = accessCodeRepository.findByCodeAndExpirationTimeAfter(accessCode, LocalDateTime.now())
                .orElseThrow(() -> new InvalidAccessException("Access code is invalid or expired."));
        User patient = code.getUser();

        // 4. Link the patient to the guardian and save
        guardian.setLinkedPatient(patient);
        userRepository.save(guardian);
    }
}
