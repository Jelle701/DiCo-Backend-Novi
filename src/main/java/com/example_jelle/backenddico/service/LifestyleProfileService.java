package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.LifestyleProfile;
import com.example_jelle.backenddico.repository.LifestyleProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class LifestyleProfileService {

    private final LifestyleProfileRepository repository;

    public LifestyleProfileService(LifestyleProfileRepository repository) {
        this.repository = repository;
    }

    public LifestyleProfile save(LifestyleProfile profile) {
        return repository.save(profile);
    }
}
