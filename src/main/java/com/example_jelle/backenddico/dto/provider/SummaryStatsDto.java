package com.example_jelle.backenddico.dto.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryStatsDto {
    private double averageGlucoseLast7Days;
    private int timeInRangePercentageLast7Days;
    // ... andere statistieken kunnen hier worden toegevoegd
}
