package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.access.GrantAccessRequestDto;
import com.example_jelle.backenddico.dto.access.GrantAccessResponseDto;
import com.example_jelle.backenddico.service.AccessService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/grant")
    public ResponseEntity<GrantAccessResponseDto> grantAccess(@Valid @RequestBody GrantAccessRequestDto request) {
        GrantAccessResponseDto response = accessService.grantAccess(request.getAccessCode());
        return ResponseEntity.ok(response);
    }
}
