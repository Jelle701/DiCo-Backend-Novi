package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

@Entity
public class MedicalProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diabetesType;
    private Integer diagnosisYear;

    // Getters and setters
}
