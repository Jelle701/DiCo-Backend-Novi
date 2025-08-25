package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface is a Spring Data JPA repository for Medication entities.
 * It provides standard CRUD (Create, Read, Update, Delete) operations for the Medication entity
 * by extending JpaRepository.
 */
public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
