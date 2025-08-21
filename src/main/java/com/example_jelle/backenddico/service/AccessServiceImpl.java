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

@Service
public class AccessServiceImpl implements AccessService {

    private final AccessCodeRepository accessCodeRepository;
    private final JwtUtil jwtUtil;

    public AccessServiceImpl(AccessCodeRepository accessCodeRepository, JwtUtil jwtUtil) {
        this.accessCodeRepository = accessCodeRepository;
        this.jwtUtil = jwtUtil;
    }

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
