package com.example_jelle.backenddico.controller; // CORRECTED PACKAGE

import com.example_jelle.backenddico.services.GlucoseDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/data/upload/glucose")
public class GlucoseDataController {

    private final GlucoseDataService glucoseDataService;

    public GlucoseDataController(GlucoseDataService glucoseDataService) {
        this.glucoseDataService = glucoseDataService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    public ResponseEntity<Map<String, String>> uploadGlucoseData(@RequestParam("file") MultipartFile file) {
        int measurementsAdded = glucoseDataService.processCsvFile(file);
        String message = String.format("Bestand succesvol verwerkt. %d metingen zijn toegevoegd.", measurementsAdded);
        return ResponseEntity.ok(Map.of("message", message));
    }
}
