package com.example_jelle.backenddico.controller; // CORRECTED PACKAGE

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.services.GlucoseDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data") // Changed from /data/upload/glucose to /data
public class GlucoseDataController {

    private final GlucoseDataService glucoseDataService;
    private static final Logger logger = LoggerFactory.getLogger(GlucoseDataController.class);

    public GlucoseDataController(GlucoseDataService glucoseDataService) {
        this.glucoseDataService = glucoseDataService;
    }

    @PostMapping("/upload/glucose") // Changed to /upload/glucose
    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    public ResponseEntity<Map<String, String>> uploadGlucoseData(@RequestParam("file") MultipartFile file) {
        int measurementsAdded = glucoseDataService.processCsvFile(file);
        String message = String.format("Bestand succesvol verwerkt. %d metingen zijn toegevoegd.", measurementsAdded);
        return ResponseEntity.ok(Map.of("message", message));
    }

    /**
     * Endpoint to get ALL glucose data for the logged-in user.
     * Secured to ensure only the data owner can request it.
     *
     * @param userDetails The details of the logged-in user, automatically injected by Spring Security.
     * @return A ResponseEntity with a list of all glucose measurements.
     */
    @GetMapping("/my-glucose-data/all")
    @PreAuthorize("hasRole('PATIENT')") // Ensures only patients can call this
    public ResponseEntity<?> getAllMyGlucoseData(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }
        
        try {
            logger.info("Export request for all glucose data for user: {}", userDetails.getUsername());
            List<GlucoseMeasurement> allGlucoseEntries = glucoseDataService.findAllByUsername(userDetails.getUsername());
            return ResponseEntity.ok(allGlucoseEntries);
        } catch (Exception e) {
            logger.error("Error fetching all glucose data for user {}: {}", userDetails.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error while fetching data.");
        }
    }
}
