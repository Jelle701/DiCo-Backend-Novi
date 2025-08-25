package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import java.util.List;

/**
 * This interface defines the contract for provider-specific operations.
 * It includes methods for linking a provider to a patient and retrieving linked patients.
 */
public interface ProviderService {

    /**
     * Links a provider to a patient using the patient's access code.
     * @param providerUsername The username of the provider performing the action.
     * @param accessCode The access code provided by the patient.
     */
    void linkPatient(String providerUsername, String accessCode);

    /**
     * Retrieves a list of all patients linked to a specific provider.
     * @param providerUsername The username of the provider.
     * @return A list of FullUserProfileDto objects representing the linked patients.
     */
    List<FullUserProfileDto> getLinkedPatients(String providerUsername);
}
