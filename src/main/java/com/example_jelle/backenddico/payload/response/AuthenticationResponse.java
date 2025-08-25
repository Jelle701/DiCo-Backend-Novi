package com.example_jelle.backenddico.payload.response;

/**
 * This class is a Data Transfer Object (DTO) for authentication responses.
 * It encapsulates the JSON Web Token (JWT) that is sent to the client after a successful login.
 */
public class AuthenticationResponse {

    /**
     * The JSON Web Token.
     */
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
