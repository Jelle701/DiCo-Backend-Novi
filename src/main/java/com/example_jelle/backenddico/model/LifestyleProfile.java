package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

/**
 * This entity represents a user's lifestyle profile.
 * It stores information about habits such as smoking, alcohol consumption, and activity level.
 */
@Entity
public class LifestyleProfile {
    /**
     * The unique identifier for the lifestyle profile record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Flag indicating if the user smokes.
     */
    private Boolean smokes;
    /**
     * Flag indicating if the user drinks alcohol.
     */
    private Boolean drinksAlcohol;
    /**
     * A description of the user's general activity level (e.g., "sedentary", "active").
     */
    private String activityLevel;

    // Getters and setters
}
