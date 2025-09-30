package com.example_jelle.backenddico.dto.provider;

// This DTO will represent the aggregated overview of all patients for a provider.
// The actual structure will depend on the data that needs to be summarized.
// For now, I'll create a placeholder class.
public class DashboardSummaryDto {
    // Example fields, these will need to be defined based on actual requirements
    private int totalPatients;
    private int patientsWithHighGlucose;
    private int patientsWithLowGlucose;

    public DashboardSummaryDto(int totalPatients, int patientsWithHighGlucose, int patientsWithLowGlucose) {
        this.totalPatients = totalPatients;
        this.patientsWithHighGlucose = patientsWithHighGlucose;
        this.patientsWithLowGlucose = patientsWithLowGlucose;
    }

    public int getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(int totalPatients) {
        this.totalPatients = totalPatients;
    }

    public int getPatientsWithHighGlucose() {
        return patientsWithHighGlucose;
    }

    public void setPatientsWithHighGlucose(int patientsWithHighGlucose) {
        this.patientsWithHighGlucose = patientsWithHighGlucose;
    }

    public int getPatientsWithLowGlucose() {
        return patientsWithLowGlucose;
    }

    public void setPatientsWithLowGlucose(int patientsWithLowGlucose) {
        this.patientsWithLowGlucose = patientsWithLowGlucose;
    }
}
