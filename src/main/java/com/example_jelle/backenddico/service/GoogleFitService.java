// Interface defining the contract for the Google Fit service.
package com.example_jelle.backenddico.service;

public interface GoogleFitService {

    // Exchanges an authorization code for access and refresh tokens from Google.
    void exchangeCodeForTokens(String code);
}
