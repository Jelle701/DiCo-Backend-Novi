package com.example_jelle.backenddico.dto.health;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyStepsDto {
    private LocalDate date;
    private Integer steps;
}
