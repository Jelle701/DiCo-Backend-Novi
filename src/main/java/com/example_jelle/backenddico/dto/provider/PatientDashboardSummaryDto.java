package com.example_jelle.backenddico.dto.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDashboardSummaryDto {
    private SummaryStatsDto summaryStats;
    private boolean hasAlerts;
    private Instant lastSyncTimestamp;
}
