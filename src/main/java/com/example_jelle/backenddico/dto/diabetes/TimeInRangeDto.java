package com.example_jelle.backenddico.dto.diabetes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeInRangeDto {
    private Integer inRangePct;
    private Integer belowRangePct;
    private Integer aboveRangePct;

    public TimeInRangeDto(Integer inRangePct, Integer belowRangePct, Integer aboveRangePct) {
        this.inRangePct = inRangePct;
        this.belowRangePct = belowRangePct;
        this.aboveRangePct = aboveRangePct;
    }

    // Getters and setters
    public Integer getInRangePct() { return inRangePct; }
    public void setInRangePct(Integer inRangePct) { this.inRangePct = inRangePct; }
    public Integer getBelowRangePct() { return belowRangePct; }
    public void setBelowRangePct(Integer belowRangePct) { this.belowRangePct = belowRangePct; }
    public Integer getAboveRangePct() { return aboveRangePct; }
    public void setAboveRangePct(Integer aboveRangePct) { this.aboveRangePct = aboveRangePct; }
}
