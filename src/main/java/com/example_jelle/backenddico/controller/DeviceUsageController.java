package com.example_jelle.backenddico.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example_jelle.backenddico.model.DeviceUsage;
import com.example_jelle.backenddico.service.DeviceUsageService;

/**
 * This controller handles requests related to user device usage.
 * It provides an endpoint to list the devices associated with the authenticated user.
 */
@RestController
@RequestMapping("/api/users/devices")
public class DeviceUsageController {
    private final DeviceUsageService service;
    public DeviceUsageController(DeviceUsageService service) {
        this.service = service;
    }

    /**
     * Lists all device usages for the currently authenticated user.
     * @param auth The authentication object for the current user.
     * @return A ResponseEntity containing a list of DeviceUsage objects, or UNAUTHORIZED if the user is not authenticated.
     */
    @GetMapping
    public ResponseEntity<List<DeviceUsage>> listDevices(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = auth.getName();
        List<DeviceUsage> usages = service.findByUserEmail(email);
        return ResponseEntity.ok(usages);
    }
}
