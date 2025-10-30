// Entity representing a user's medical profile, focusing on diabetes-related information.
package com.example_jelle.backenddico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class MedicalProfile {
    // The unique identifier for the medical profile record.
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private User user;

    // The type of diabetes the user has.
    private String diabetesType;
    // The year the user was diagnosed with diabetes.
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


    // Gets the ID of the medical profile.
    public Long getId() {
        return id;
    }

    // Sets the ID of the medical profile.
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the user associated with this medical profile.
    public User getUser() {
        return user;
    }

    // Sets the user associated with this medical profile.
    public void setUser(User user) {
        this.user = user;
    }

    // Gets the diabetes type.
    public String getDiabetesType() {
        return diabetesType;
    }

    // Sets the diabetes type.
    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    // Gets the diagnosis year.
    public Integer getDiagnosisYear() {
        return diagnosisYear;
    }

    // Sets the diagnosis year.
    public void setDiagnosisYear(Integer diagnosisYear) {
        this.diagnosisYear = diagnosisYear;
    }

    // Gets the HbA1c value.
    public Double getHbA1c() {
        return hbA1c;
    }

    // Sets the HbA1c value.
    public void setHbA1c(Double hbA1c) {
        this.hbA1c = hbA1c;
    }

    // Gets the carbohydrates per day in grams.
    public Integer getCarbsPerDayGrams() {
        return carbsPerDayGrams;
    }

    // Sets the carbohydrates per day in grams.
    public void setCarbsPerDayGrams(Integer carbsPerDayGrams) {
        this.carbsPerDayGrams = carbsPerDayGrams;
    }

    // Gets the activity minutes.
    public Integer getActivityMinutes() {
        return activityMinutes;
    }

    // Sets the activity minutes.
    public void setActivityMinutes(Integer activityMinutes) {
        this.activityMinutes = activityMinutes;
    }

    // Gets the sleep hours.
    public Double getSleepHours() {
        return sleepHours;
    }

    // Sets the sleep hours.
    public void setSleepHours(Double sleepHours) {
        this.sleepHours = sleepHours;
    }

    // Gets the stress score.
    public Integer getStressScore() {
        return stressScore;
    }

    // Sets the stress score.
    public void setStressScore(Integer stressScore) {
        this.stressScore = stressScore;
    }

    // Gets the systolic blood pressure.
    public Integer getSystolic() {
        return systolic;
    }

    // Sets the systolic blood pressure.
    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    // Gets the diastolic blood pressure.
    public Integer getDiastolic() {
        return diastolic;
    }

    // Sets the diastolic blood pressure.
    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    // Gets the weight in kilograms.
    public Double getWeightKg() {
        return weightKg;
    }

    // Sets the weight in kilograms.
    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    // Gets the last updated timestamp.
    public Instant getLastUpdated() {
        return lastUpdated;
    }

    // Sets the last updated timestamp.
    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
