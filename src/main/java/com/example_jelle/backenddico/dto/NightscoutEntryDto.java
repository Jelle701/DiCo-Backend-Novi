// Data Transfer Object for a single glucose entry from a Nightscout-compatible source.
package com.example_jelle.backenddico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Date;

public class NightscoutEntryDto {

    // The glucose value in mg/dL.
    @JsonProperty("sgv")
    private int sgv;

    // The timestamp of the reading, provided as epoch milliseconds from the source.
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date date;

    // The identifier of the device that created the entry.
    @JsonProperty("device")
    private String device;

    // Gets the glucose value.
    public int getSgv() {
        return sgv;
    }

    // Sets the glucose value.
    public void setSgv(int sgv) {
        this.sgv = sgv;
    }

    // Gets the timestamp of the reading.
    public Date getDate() {
        return date;
    }

    // Sets the timestamp of the reading.
    public void setDate(Date date) {
        this.date = date;
    }

    // Gets the device identifier.
    public String getDevice() {
        return device;
    }

    // Sets the device identifier.
    public void setDevice(String device) {
        this.device = device;
    }

    // Converts the Date object to an Instant.
    public Instant getDateAsInstant() {
        return date != null ? date.toInstant() : null;
    }
}
