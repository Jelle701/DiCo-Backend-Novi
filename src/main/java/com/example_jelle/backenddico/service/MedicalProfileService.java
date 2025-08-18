package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.MedicalProfile;
import com.example_jelle.backenddico.repository.MedicalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalProfileService {

    private final MedicalProfileRepository repository;

    public MedicalProfileService(MedicalProfileRepository repository) {
        this.repository = repository;
    }

    public MedicalProfile save(MedicalProfile profile) {
        return repository.save(profile);
    }
}
