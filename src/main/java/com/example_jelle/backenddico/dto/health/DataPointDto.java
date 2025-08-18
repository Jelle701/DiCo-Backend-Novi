package com.example_jelle.backenddico.dto.health;

import lombok.Data;
import java.time.Instant;

@Data
public class DataPointDto {
    private Instant timestamp;
    private Double value;
}
