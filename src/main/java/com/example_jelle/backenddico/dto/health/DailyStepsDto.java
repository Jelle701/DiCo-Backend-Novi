// Data Transfer Object for daily step counts.
package com.example_jelle.backenddico.dto.health;

import java.time.LocalDate;

public class DailyStepsDto {
    // The date for which the step count is recorded.
    private LocalDate date;
    // The total number of steps for the given date.
    private Integer steps;

    // Gets the date.
    public LocalDate getDate() {
        return date;
    }

    // Sets the date.
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Gets the number of steps.
    public Integer getSteps() {
        return steps;
    }

    // Sets the number of steps.
    public void setSteps(Integer steps) {
        this.steps = steps;
    }
}
