package com.example_jelle.backenddico.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String measurementSystem; // "metric" or "imperial"
    private String gender;
    private Double weight;
    private Double height;
    private Double bmi;

    // Velden voor medicatiegegevens
    private String diabetesType;
    private String longActingInsulin;
    private String shortActingInsulin;

    @Column(unique = true) // Elk secret moet uniek zijn
    private String apiSecretHash;

    // Getters en Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getMeasurementSystem() { return measurementSystem; }
    public void setMeasurementSystem(String measurementSystem) { this.measurementSystem = measurementSystem; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public Double getBmi() { return bmi; }
    public void setBmi(Double bmi) { this.bmi = bmi; }

    public String getDiabetesType() { return diabetesType; }
    public void setDiabetesType(String diabetesType) { this.diabetesType = diabetesType; }

    public String getLongActingInsulin() { return longActingInsulin; }
    public void setLongActingInsulin(String longActingInsulin) { this.longActingInsulin = longActingInsulin; }

    public String getShortActingInsulin() { return shortActingInsulin; }
    public void setShortActingInsulin(String shortActingInsulin) { this.shortActingInsulin = shortActingInsulin; }

    public String getApiSecretHash() { return apiSecretHash; }
    public void setApiSecretHash(String apiSecretHash) { this.apiSecretHash = apiSecretHash; }
}