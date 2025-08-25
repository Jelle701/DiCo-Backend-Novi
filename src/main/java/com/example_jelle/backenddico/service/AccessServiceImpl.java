package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.access.GrantAccessResponseDto;
import com.example_jelle.backenddico.exception.InvalidAccessException;
import com.example_jelle.backenddico.model.AccessCode;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.AccessCodeRepository;
import com.example_jelle.backenddico.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * This service implements the logic for granting access using an access code.
 * It validates the code and generates a delegated JWT for the user associated with the code.
 */
@Service
public class AccessServiceImpl implements AccessService {

    private final AccessCodeRepository accessCodeRepository;
    private final JwtUtil jwtUtil;

    public AccessServiceImpl(AccessCodeRepository accessCodeRepository, JwtUtil jwtUtil) {
        this.accessCodeRepository = accessCodeRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Grants access based on a provided access code.
     * It finds a valid, non-expired access code, retrieves the associated patient,
     * and generates a delegated JWT for that patient.
     * @param accessCode The access code to validate.
     * @return A DTO containing the delegated JWT and the patient's username.
     * @throws InvalidAccessException if the code is invalid or expired.
     */
    @Override
    @Transactional(readOnly = true)
    public GrantAccessResponseDto grantAccess(String accessCode) {
        AccessCode code = accessCodeRepository.findByCodeAndExpirationTimeAfter(accessCode, LocalDateTime.now())
                .orElseThrow(() -> new InvalidAccessException("Access code is invalid or expired."));

        User patient = code.getUser();

        // Generate a delegated token with a special claim indicating the target patient
        String delegatedToken = jwtUtil.generateDelegatedToken(patient.getUsername());

        return new GrantAccessResponseDto(delegatedToken, patient.getUsername());
    }
}
