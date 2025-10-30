// Data Transfer Object for authentication responses.
package com.example_jelle.backenddico.payload.response;

public class AuthenticationResponse {

    // The JSON Web Token.
    private final String jwt;

    // Constructs a new AuthenticationResponse.
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    // Gets the JWT.
    public String getJwt() {
        return jwt;
    }
}
