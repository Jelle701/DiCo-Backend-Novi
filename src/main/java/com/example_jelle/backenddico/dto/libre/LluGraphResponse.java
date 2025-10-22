package com.example_jelle.backenddico.dto.libre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LluGraphResponse(GraphData data) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GraphData(
        List<Measurement> measurement, 
        @JsonProperty("AmperageMeasurements") List<Measurement> amperageMeasurements
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Measurement(
        @JsonProperty("Timestamp") LocalDateTime timestamp,
        @JsonProperty("Value") double value
    ) {}
}
