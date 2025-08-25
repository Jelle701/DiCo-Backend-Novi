package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.access.GrantAccessResponseDto;

/**
 * This interface defines the contract for the access granting mechanism.
 * It provides a method to grant access based on an access code.
 */
public interface AccessService {

    /**
     * Grants access based on a provided access code.
     * @param accessCode The access code to be validated.
     * @return A DTO containing the result of the access grant, typically a token.
     */
    GrantAccessResponseDto grantAccess(String accessCode);
}
