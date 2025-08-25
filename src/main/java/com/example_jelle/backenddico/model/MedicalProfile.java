package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

/**
 * This entity represents a user's medical profile, focusing on diabetes-related information.
 * It stores the type of diabetes and the year of diagnosis.
 */
@Entity
public class MedicalProfile {
    /**
     * The unique identifier for the medical profile record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of diabetes the user has (e.g., "Type 1", "Type 2").
     */
    private String diabetesType;
    /**
     * The year the user was diagnosed with diabetes.
     */
    private Integer diagnosisYear;

    // Getters and setters
}
