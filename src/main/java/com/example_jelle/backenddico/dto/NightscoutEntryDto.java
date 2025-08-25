package com.example_jelle.backenddico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Date;

/**
 * This class is a Data Transfer Object (DTO) representing a single glucose entry from a Nightscout-compatible source.
 * The field names are mapped to the JSON properties of the Nightscout API specification.
 */
public class NightscoutEntryDto {

    /**
     * The glucose value in mg/dL.
     */
    @JsonProperty("sgv")
    private int sgv;

    /**
     * The timestamp of the reading, provided as epoch milliseconds from the source.
     */
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER) // Nightscout sends date as epoch milliseconds
    private Date date;

    /**
     * The identifier of the device that created the entry (e.g., "nightscout-librelink-up").
     */
    @JsonProperty("device")
    private String device;

    // Getters and Setters
    public int getSgv() {
        return sgv;
    }

    public void setSgv(int sgv) {
        this.sgv = sgv;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * A helper method to convert the Date object to an Instant.
     * This is useful for working with Java's modern date-time API (java.time).
     * @return The date as an Instant, or null if the date is not set.
     */
    public Instant getDateAsInstant() {
        return date != null ? date.toInstant() : null;
    }
}
