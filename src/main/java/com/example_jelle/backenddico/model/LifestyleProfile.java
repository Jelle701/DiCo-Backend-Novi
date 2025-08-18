package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

@Entity
public class LifestyleProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean smokes;
    private Boolean drinksAlcohol;
    private String activityLevel;

    // Getters and setters
}
