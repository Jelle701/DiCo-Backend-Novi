package com.example_jelle.backenddico.service;

/**
 * This interface defines the contract for the Google Fit service.
 * It includes methods for handling the OAuth2 flow, such as exchanging an authorization code for tokens.
 */
public interface GoogleFitService {

    /**
     * Exchanges an authorization code for access and refresh tokens from Google.
     * @param code The authorization code obtained from the Google OAuth2 callback.
     */
    void exchangeCodeForTokens(String code);
}
