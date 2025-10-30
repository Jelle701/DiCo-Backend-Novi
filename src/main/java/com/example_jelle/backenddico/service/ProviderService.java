// Interface defining the contract for provider-specific operations.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;
import com.example_jelle.backenddico.dto.provider.DashboardSummaryDto;
import com.example_jelle.backenddico.dto.provider.DelegatedTokenResponseDto;
import com.example_jelle.backenddico.dto.provider.PatientDashboardSummaryDto;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;

import java.util.List;

public interface ProviderService {

    // Links a provider to a patient using the patient's access code.
    void linkPatient(String providerUsername, String accessCode);

    // Retrieves a list of all patients linked to a specific provider.
    List<FullUserProfileDto> getLinkedPatients(String providerUsername);

    // Generates a temporary, patient-specific token for a linked patient.
    DelegatedTokenResponseDto generateDelegatedToken(String providerUsername, Long patientId);

    // Retrieves an aggregated overview of all patients linked to the authenticated provider.
    DashboardSummaryDto getDashboardSummary(String providerUsername);

    // Retrieves a dashboard summary for a specific patient linked to the authenticated provider.
    PatientDashboardSummaryDto getPatientDashboardSummary(String providerUsername, Long patientId);

    // Retrieves all glucose measurements for a specific patient, if the requesting user is linked to them.
    List<GlucoseMeasurementDto> getGlucoseMeasurementsForPatient(String requestingUsername, Long patientId);

    // Retrieves the diabetes summary for a specific patient, if the requesting provider is linked to them.
    DiabetesSummaryDto getDiabetesSummaryForPatient(String providerUsername, Long patientId);
}
