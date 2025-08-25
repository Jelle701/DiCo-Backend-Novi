package com.example_jelle.backenddico.service;

/**
 * This interface defines the contract for guardian-specific operations.
 * It includes methods for linking a guardian to a patient account.
 */
public interface GuardianService {

    /**
     * Links a guardian to a patient using the patient's access code.
     * @param guardianUsername The username of the guardian performing the action.
     * @param accessCode The access code provided by the patient.
     */
    void linkPatient(String guardianUsername, String accessCode);
}
