package com.example_jelle.backenddico.dto.provider;

public class SummaryStatsDto {
    private double averageGlucoseLast7Days;
    private int timeInRangePercentageLast7Days;

    public SummaryStatsDto() {}

    // Constructor for (double, int) call
    public SummaryStatsDto(double averageGlucoseLast7Days, int timeInRangePercentageLast7Days) {
        this.averageGlucoseLast7Days = averageGlucoseLast7Days;
        this.timeInRangePercentageLast7Days = timeInRangePercentageLast7Days;
    }

    // Overloaded constructor for (int, int) call - to fix the build error
    public SummaryStatsDto(int averageGlucose, int timeInRangePercentage) {
        this.averageGlucoseLast7Days = (double) averageGlucose;
        this.timeInRangePercentageLast7Days = timeInRangePercentage;
    }

    public double getAverageGlucoseLast7Days() {
        return averageGlucoseLast7Days;
    }

    public void setAverageGlucoseLast7Days(double averageGlucoseLast7Days) {
        this.averageGlucoseLast7Days = averageGlucoseLast7Days;
    }

    public int getTimeInRangePercentageLast7Days() {
        return timeInRangePercentageLast7Days;
    }

    public void setTimeInRangePercentageLast7Days(int timeInRangePercentageLast7Days) {
        this.timeInRangePercentageLast7Days = timeInRangePercentageLast7Days;
    }
}
