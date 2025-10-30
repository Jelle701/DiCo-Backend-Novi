// Data Transfer Object for lifestyle data.
package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LifestyleDto {
    private Integer carbsPerDayGrams;
    private Integer activityMinutes;
    private Double sleepHours;
    private Integer stressScore;

    // Constructs a new LifestyleDto.
    public LifestyleDto(Integer carbsPerDayGrams, Integer activityMinutes, Double sleepHours, Integer stressScore) {
        this.carbsPerDayGrams = carbsPerDayGrams;
        this.activityMinutes = activityMinutes;
        this.sleepHours = sleepHours;
        this.stressScore = stressScore;
    }

    // Gets the carbohydrates per day in grams.
    public Integer getCarbsPerDayGrams() { return carbsPerDayGrams; }
    // Sets the carbohydrates per day in grams.
    public void setCarbsPerDayGrams(Integer carbsPerDayGrams) { this.carbsPerDayGrams = carbsPerDayGrams; }
    // Gets the activity minutes.
    public Integer getActivityMinutes() { return activityMinutes; }
    // Sets the activity minutes.
    public void setActivityMinutes(Integer activityMinutes) { this.activityMinutes = activityMinutes; }
    // Gets the sleep hours.
    public Double getSleepHours() { return sleepHours; }
    // Sets the sleep hours.
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }
    // Gets the stress score.
    public Integer getStressScore() { return stressScore; }
    // Sets the stress score.
    public void setStressScore(Integer stressScore) { this.stressScore = stressScore; }
}
