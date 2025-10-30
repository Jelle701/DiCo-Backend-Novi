// Data Transfer Object for Time-In-Range data.
package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeInRangeDto {
    private Integer inRangePct;
    private Integer belowRangePct;
    private Integer aboveRangePct;

    // Constructs a new TimeInRangeDto.
    public TimeInRangeDto(Integer inRangePct, Integer belowRangePct, Integer aboveRangePct) {
        this.inRangePct = inRangePct;
        this.belowRangePct = belowRangePct;
        this.aboveRangePct = aboveRangePct;
    }

    // Gets the percentage of time in range.
    public Integer getInRangePct() { return inRangePct; }
    // Sets the percentage of time in range.
    public void setInRangePct(Integer inRangePct) { this.inRangePct = inRangePct; }
    // Gets the percentage of time below range.
    public Integer getBelowRangePct() { return belowRangePct; }
    // Sets the percentage of time below range.
    public void setBelowRangePct(Integer belowRangePct) { this.belowRangePct = belowRangePct; }
    // Gets the percentage of time above range.
    public Integer getAboveRangePct() { return aboveRangePct; }
    // Sets the percentage of time above range.
    public void setAboveRangePct(Integer aboveRangePct) { this.aboveRangePct = aboveRangePct; }
}
