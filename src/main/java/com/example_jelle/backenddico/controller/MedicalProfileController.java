package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.model.MedicalProfile;
import com.example_jelle.backenddico.service.MedicalProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-profiles")
public class MedicalProfileController {

    private final MedicalProfileService service;

    public MedicalProfileController(MedicalProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MedicalProfile> create(@RequestBody MedicalProfile profile) {
        return ResponseEntity.ok(service.save(profile));
    }
}
