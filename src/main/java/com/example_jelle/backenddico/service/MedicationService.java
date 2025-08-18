package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.Medication;
import com.example_jelle.backenddico.repository.MedicationRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicationService {

    private final MedicationRepository repository;

    public MedicationService(MedicationRepository repository) {
        this.repository = repository;
    }

    public Medication save(Medication medication) {
        return repository.save(medication);
    }
}
