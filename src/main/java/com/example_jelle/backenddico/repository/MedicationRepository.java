package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
