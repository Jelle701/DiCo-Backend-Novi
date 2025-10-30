// Service for managing medical profiles.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.MedicalProfile;
import com.example_jelle.backenddico.repository.MedicalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalProfileService {

    private final MedicalProfileRepository repository;

    // Constructs a new MedicalProfileService.
    public MedicalProfileService(MedicalProfileRepository repository) {
        this.repository = repository;
    }

    // Saves a medical profile.
    public MedicalProfile save(MedicalProfile profile) {
        return repository.save(profile);
    }
}
