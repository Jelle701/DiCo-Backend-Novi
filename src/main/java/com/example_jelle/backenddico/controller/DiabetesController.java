package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;
import com.example_jelle.backenddico.exception.DataNotFoundException;
import com.example_jelle.backenddico.service.DiabetesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/diabetes")
public class DiabetesController {

    private final DiabetesService diabetesService;

    public DiabetesController(DiabetesService diabetesService) {
        this.diabetesService = diabetesService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DiabetesSummaryDto> getDiabetesSummary(Authentication authentication) {
        String username = authentication.getName();
        DiabetesSummaryDto summary = diabetesService.getDiabetesSummary(username);
        return ResponseEntity.ok(summary);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDataNotFoundException(DataNotFoundException ex) {
        return new ResponseEntity<>(Map.of("message", "Geen samenvattingsgegevens beschikbaar voor deze gebruiker."), HttpStatus.NOT_FOUND);
    }
}
