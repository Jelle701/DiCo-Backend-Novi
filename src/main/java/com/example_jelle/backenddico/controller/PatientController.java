package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final UserService userService;

    public PatientController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/access-code/generate")
    public ResponseEntity<Map<String, String>> generateAccessCode(Principal principal) {
        // Logic to be implemented in UserService
        // String accessCode = userService.generateAccessCode(principal.getName());
        // return ResponseEntity.ok(Map.of("accessCode", accessCode));
        return ResponseEntity.ok(Map.of("message", "Endpoint created, logic to be implemented"));
    }

    @GetMapping("/access-code")
    public ResponseEntity<Map<String, String>> getAccessCode(Principal principal) {
        // Logic to be implemented in UserService
        // String accessCode = userService.getAccessCode(principal.getName());
        // return ResponseEntity.ok(Map.of("accessCode", accessCode));
        return ResponseEntity.ok(Map.of("message", "Endpoint created, logic to be implemented"));
    }
}
