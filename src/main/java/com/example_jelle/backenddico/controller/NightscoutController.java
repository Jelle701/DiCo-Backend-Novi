package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.NightscoutEntryDto;
import com.example_jelle.backenddico.service.NightscoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NightscoutController {

    private final NightscoutService nightscoutService;

    public NightscoutController(NightscoutService nightscoutService) {
        this.nightscoutService = nightscoutService;
    }

    /**
     * Endpoint to receive glucose entries from a Nightscout-compatible uploader.
     * Authentication is handled via the 'api-secret' header.
     */
    @PostMapping("/entries")
    public ResponseEntity<Void> receiveEntries(
            @RequestHeader("api-secret") String apiSecret,
            @RequestBody List<NightscoutEntryDto> entries) {

        nightscoutService.processEntries(apiSecret, entries);
        return ResponseEntity.ok().build();
    }
}