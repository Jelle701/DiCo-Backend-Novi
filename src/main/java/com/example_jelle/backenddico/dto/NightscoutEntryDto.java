package com.example_jelle.backenddico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Date;

/**
 * DTO to represent a single entry from a Nightscout-compatible uploader.
 * Field names match the Nightscout API specification.
 */
public class NightscoutEntryDto {

    // Glucose Value in mg/dL, as sent by most uploaders.
    @JsonProperty("sgv")
    private int sgv;

    // The timestamp of the reading.
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER) // Nightscout sends date as epoch milliseconds
    private Date date;

    // The device that created the entry, e.g., "nightscout-librelink-up"
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

    // Helper method to convert to an Instant for easier use with LocalDateTime
    public Instant getDateAsInstant() {
        return date != null ? date.toInstant() : null;
    }
}