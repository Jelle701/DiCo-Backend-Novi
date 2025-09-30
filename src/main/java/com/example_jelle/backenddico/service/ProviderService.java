package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.provider.DashboardSummaryDto;
import com.example_jelle.backenddico.dto.provider.DelegatedTokenResponseDto;
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

    /**
     * Generates a temporary, patient-specific token for a linked patient.
     * @param providerUsername The username of the provider requesting the token.
     * @param patientId The ID of the patient for whom to generate the delegated token.
     * @return A DelegatedTokenResponseDto containing the delegated token and patient username.
     */
    DelegatedTokenResponseDto generateDelegatedToken(String providerUsername, Long patientId);

    /**
     * Retrieves an aggregated overview of all patients linked to the authenticated provider.
     * @param providerUsername The username of the provider.
     * @return A DashboardSummaryDto containing the aggregated summary.
     */
    DashboardSummaryDto getDashboardSummary(String providerUsername);
}
