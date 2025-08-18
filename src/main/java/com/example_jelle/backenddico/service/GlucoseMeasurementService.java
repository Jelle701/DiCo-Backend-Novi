package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;

import java.util.List;

public interface GlucoseMeasurementService {

    /**
     * Voegt een nieuwe glucosemeting toe voor de opgegeven gebruiker.
     *
     * @param userEmail De email van de ingelogde gebruiker.
     * @param measurementDto De DTO met de meetgegevens.
     * @return De opgeslagen meting als DTO.
     */
    GlucoseMeasurementDto addMeasurement(String userEmail, GlucoseMeasurementDto measurementDto);

    /**
     * Haalt de glucosemetingen van de laatste 90 dagen op voor de opgegeven gebruiker.
     *
     * @param userEmail De email van de ingelogde gebruiker.
     * @return Een lijst met recente metingen.
     */
    List<GlucoseMeasurementDto> getRecentMeasurements(String userEmail);
}