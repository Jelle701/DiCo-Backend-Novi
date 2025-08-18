package com.example_jelle.backenddico.dto.health;

import lombok.Data;

import java.util.List;

@Data
public class HealthDataRequest {
    private List<DataPointDto> steps;
    private List<DataPointDto> heartRate;
}
