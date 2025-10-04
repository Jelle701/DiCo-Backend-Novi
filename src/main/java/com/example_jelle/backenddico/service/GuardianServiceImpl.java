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
 * @deprecated This logic has been unified into ProviderServiceImpl. This class is now obsolete.
 */
@Service
@Deprecated
public class GuardianServiceImpl implements GuardianService {

    public GuardianServiceImpl() {
        // This service is deprecated and should not be used.
    }

    /**
     * @deprecated All linking logic is now handled by ProviderServiceImpl#linkPatient.
     */
    @Override
    @Transactional
    @Deprecated
    public void linkPatient(String guardianUsername, String accessCode) {
        // This method is deprecated and its logic has been moved to ProviderServiceImpl.
        // It is left empty to resolve compilation errors.
        throw new UnsupportedOperationException("This method is deprecated. Use ProviderService instead.");
    }
}
