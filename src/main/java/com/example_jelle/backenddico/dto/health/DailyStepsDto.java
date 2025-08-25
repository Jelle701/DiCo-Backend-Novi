package com.example_jelle.backenddico.dto.health;

import lombok.Data;

import java.time.LocalDate;

/**
 * This class is a Data Transfer Object (DTO) for daily step counts.
 * It is used to represent the total number of steps taken on a specific date.
 */
@Data
public class DailyStepsDto {
    /**
     * The date for which the step count is recorded.
     */
    private LocalDate date;
    /**
     * The total number of steps for the given date.
     */
    private Integer steps;
}
