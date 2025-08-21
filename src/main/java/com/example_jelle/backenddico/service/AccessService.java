package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.access.GrantAccessResponseDto;

public interface AccessService {
    GrantAccessResponseDto grantAccess(String accessCode);
}
