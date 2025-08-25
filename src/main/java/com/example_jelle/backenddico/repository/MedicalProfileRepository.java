package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.MedicalProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface is a Spring Data JPA repository for MedicalProfile entities.
 * It provides standard CRUD (Create, Read, Update, Delete) operations for the MedicalProfile entity
 * by extending JpaRepository.
 */
public interface MedicalProfileRepository extends JpaRepository<MedicalProfile, Long> {
}
