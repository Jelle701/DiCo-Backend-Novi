package com.example_jelle.backenddico.dto.provider;

// import lombok.AllArgsConstructor; // Tijdelijk verwijderd
// import lombok.Data; // Tijdelijk verwijderd
// import lombok.NoArgsConstructor; // Tijdelijk verwijderd

import java.time.Instant;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class PatientDashboardSummaryDto {
    private SummaryStatsDto summaryStats;
    private boolean hasAlerts;
    private Instant lastSyncTimestamp;

    public PatientDashboardSummaryDto() {}

    public PatientDashboardSummaryDto(SummaryStatsDto summaryStats, boolean hasAlerts, Instant lastSyncTimestamp) {
        this.summaryStats = summaryStats;
        this.hasAlerts = hasAlerts;
        this.lastSyncTimestamp = lastSyncTimestamp;
    }

    public SummaryStatsDto getSummaryStats() {
        return summaryStats;
    }

    public void setSummaryStats(SummaryStatsDto summaryStats) {
        this.summaryStats = summaryStats;
    }

    public boolean isHasAlerts() {
        return hasAlerts;
    }

    public void setHasAlerts(boolean hasAlerts) {
        this.hasAlerts = hasAlerts;
    }

    public Instant getLastSyncTimestamp() {
        return lastSyncTimestamp;
    }

    public void setLastSyncTimestamp(Instant lastSyncTimestamp) {
        this.lastSyncTimestamp = lastSyncTimestamp;
    }
}
