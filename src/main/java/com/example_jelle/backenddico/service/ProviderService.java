package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;
import com.example_jelle.backenddico.dto.provider.DashboardSummaryDto;
import com.example_jelle.backenddico.dto.provider.DelegatedTokenResponseDto;
import com.example_jelle.backenddico.dto.provider.PatientDashboardSummaryDto;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.GlucoseMeasurementDto; // Import the DTO

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

    /**
     * Retrieves a dashboard summary for a specific patient linked to the authenticated provider.
     * @param providerUsername The username of the provider.
     * @param patientId The ID of the patient for whom to retrieve the summary.
     * @return A PatientDashboardSummaryDto containing the summary for the specific patient.
     */
    PatientDashboardSummaryDto getPatientDashboardSummary(String providerUsername, Long patientId);

    /**
     * Retrieves all glucose measurements for a specific patient, but only if the requesting user is linked to them.
     * @param requestingUsername The username of the GUARDIAN or PROVIDER.
     * @param patientId The ID of the patient whose measurements are being requested.
     * @return A list of GlucoseMeasurementDto objects.
     */
    List<GlucoseMeasurementDto> getGlucoseMeasurementsForPatient(String requestingUsername, Long patientId);

    /**
     * Retrieves the diabetes summary for a specific patient, but only if the requesting provider is linked to them.
     * @param providerUsername The username of the PROVIDER.
     * @param patientId The ID of the patient whose summary is being requested.
     * @return A DiabetesSummaryDto containing the calculated summary.
     */
    DiabetesSummaryDto getDiabetesSummaryForPatient(String providerUsername, Long patientId);
}
