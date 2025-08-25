package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.LifestyleProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface is a Spring Data JPA repository for LifestyleProfile entities.
 * It provides standard CRUD (Create, Read, Update, Delete) operations for the LifestyleProfile entity
 * by extending JpaRepository.
 */
public interface LifestyleProfileRepository extends JpaRepository<LifestyleProfile, Long> {
}
