package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.NightscoutEntryDto;

import java.util.List;

public interface NightscoutService {

    /**
     * Processes a batch of glucose entries sent from a Nightscout-compatible uploader.
     *
     * @param apiSecret The raw API secret provided by the uploader.
     * @param entries   A list of glucose entries.
     */
    void processEntries(String apiSecret, List<NightscoutEntryDto> entries);
}