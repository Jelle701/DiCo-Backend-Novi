package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/generate-access-code")
    public ResponseEntity<?> generateAccessCode(Authentication authentication) {
        String username = authentication.getName();
        String accessCode = userService.generateAccessCode(username);
        return ResponseEntity.ok(Map.of("accessCode", accessCode));
    }

    @GetMapping("/access-code")
    public ResponseEntity<?> getAccessCode(Authentication authentication) {
        String username = authentication.getName();
        return userService.getAccessCode(username)
                .map(code -> ResponseEntity.ok(Map.of("accessCode", code)))
                .orElse(ResponseEntity.ok(Map.of("accessCode", (Object) null)));
    }
}
