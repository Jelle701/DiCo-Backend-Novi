package com.example_jelle.backenddico.dto.health;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDataResponse {
    private List<DailyStepsDto> stepsLast7Days;
    private DataPointDto latestHeartRate;
}
