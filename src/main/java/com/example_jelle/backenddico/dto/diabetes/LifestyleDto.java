package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LifestyleDto {
    private Integer carbsPerDayGrams;
    private Integer activityMinutes;
    private Double sleepHours;
    private Integer stressScore;

    public LifestyleDto(Integer carbsPerDayGrams, Integer activityMinutes, Double sleepHours, Integer stressScore) {
        this.carbsPerDayGrams = carbsPerDayGrams;
        this.activityMinutes = activityMinutes;
        this.sleepHours = sleepHours;
        this.stressScore = stressScore;
    }

    // Getters and setters
    public Integer getCarbsPerDayGrams() { return carbsPerDayGrams; }
    public void setCarbsPerDayGrams(Integer carbsPerDayGrams) { this.carbsPerDayGrams = carbsPerDayGrams; }
    public Integer getActivityMinutes() { return activityMinutes; }
    public void setActivityMinutes(Integer activityMinutes) { this.activityMinutes = activityMinutes; }
    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }
    public Integer getStressScore() { return stressScore; }
    public void setStressScore(Integer stressScore) { this.stressScore = stressScore; }
}
