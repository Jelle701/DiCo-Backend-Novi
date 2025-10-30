// Interface defining the contract for the access granting mechanism.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.access.GrantAccessResponseDto;

public interface AccessService {

    // Grants access based on a provided access code.
    GrantAccessResponseDto grantAccess(String accessCode);
}
