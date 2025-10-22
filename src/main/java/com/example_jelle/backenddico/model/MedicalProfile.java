package com.example_jelle.backenddico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;

/**
 * This entity represents a user's medical profile, focusing on diabetes-related information.
 * It stores the type of diabetes, the year of diagnosis, and other health metrics.
 */
@Entity
public class MedicalProfile {
    /**
     * The unique identifier for the medical profile record.
     */
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private User user;

    /**
     * The type of diabetes the user has (e.g., "Type 1", "Type 2").
     */
    private String diabetesType;
    /**
     * The year the user was diagnosed with diabetes.
     */
    private Integer diagnosisYear;

    // New fields for the diabetes summary
    private Double hbA1c; // Assuming unit is always '%'

    private Integer carbsPerDayGrams;
    private Integer activityMinutes;
    private Double sleepHours;
    private Integer stressScore;

    private Integer systolic;
    private Integer diastolic;
    private Double weightKg;

    private Instant lastUpdated;


    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public Integer getDiagnosisYear() {
        return diagnosisYear;
    }

    public void setDiagnosisYear(Integer diagnosisYear) {
        this.diagnosisYear = diagnosisYear;
    }

    public Double getHbA1c() {
        return hbA1c;
    }

    public void setHbA1c(Double hbA1c) {
        this.hbA1c = hbA1c;
    }

    public Integer getCarbsPerDayGrams() {
        return carbsPerDayGrams;
    }

    public void setCarbsPerDayGrams(Integer carbsPerDayGrams) {
        this.carbsPerDayGrams = carbsPerDayGrams;
    }

    public Integer getActivityMinutes() {
        return activityMinutes;
    }

    public void setActivityMinutes(Integer activityMinutes) {
        this.activityMinutes = activityMinutes;
    }

    public Double getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(Double sleepHours) {
        this.sleepHours = sleepHours;
    }

    public Integer getStressScore() {
        return stressScore;
    }

    public void setStressScore(Integer stressScore) {
        this.stressScore = stressScore;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
