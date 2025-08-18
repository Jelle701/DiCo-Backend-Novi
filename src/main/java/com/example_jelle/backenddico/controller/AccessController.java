package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.AccessCodeRequest;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.security.JwtUtil;
import com.example_jelle.backenddico.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AccessController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/grant")
    public ResponseEntity<?> grantAccess(@RequestBody AccessCodeRequest request) {
        return userService.findByAccessCode(request.getAccessCode())
                .map(this::createDelegatedToken)
                .map(token -> ResponseEntity.ok(Map.of("token", token)))
                .orElse(ResponseEntity.notFound().build());
    }

    private String createDelegatedToken(User user) {
        long expirationMillis = 8 * 60 * 60 * 1000; // 8 uur
        List<String> authorities = List.of("read:dashboard");
        return jwtUtil.generateToken(user.getUsername(), authorities, expirationMillis);
    }
}
