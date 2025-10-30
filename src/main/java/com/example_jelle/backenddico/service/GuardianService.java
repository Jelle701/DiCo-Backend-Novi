// Interface defining the contract for guardian-specific operations.
package com.example_jelle.backenddico.service;

public interface GuardianService {

    // Links a guardian to a patient using the patient's access code.
    void linkPatient(String guardianUsername, String accessCode);
}
