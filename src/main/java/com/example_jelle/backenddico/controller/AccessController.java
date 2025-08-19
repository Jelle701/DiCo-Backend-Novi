package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.AccessCodeRequest;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    private final UserService userService;

    public AccessController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/grant")
    public ResponseEntity<?> grantAccess(@RequestBody AccessCodeRequest request) {
        try {
            String delegatedToken = userService.grantDelegatedAccessToken(request.getAccessCode());
            return ResponseEntity.ok(Map.of("token", delegatedToken));
        } catch (RuntimeException e) {
            // Catches RecordNotFoundException from the service layer
            return ResponseEntity.notFound().build();
        }
    }
}
