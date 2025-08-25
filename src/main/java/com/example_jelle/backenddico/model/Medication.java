package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

/**
 * This entity represents a medication that a user might be taking.
 * It stores basic information about the medication like its name and dosage.
 */
@Entity
public class Medication {
    /**
     * The unique identifier for the medication record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the medication.
     */
    private String name;
    /**
     * The prescribed dosage of the medication (e.g., "10mg", "1 tablet").
     */
    private String dosage;

    // Getters and setters
}
