package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.service.GoogleFitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * This controller handles the OAuth2 callback from Google for the Google Fit integration.
 */
@Controller
@RequestMapping("/auth/google")
public class GoogleFitController {

    private final GoogleFitService googleFitService;

    public GoogleFitController(GoogleFitService googleFitService) {
        this.googleFitService = googleFitService;
    }

    /**
     * Handles the callback from Google after the user has granted permission.
     * It receives the authorization code, exchanges it for access and refresh tokens,
     * and then redirects the user back to the frontend application.
     * @param code The authorization code provided by Google.
     * @return A RedirectView that sends the user to the frontend application with a success status.
     */
    @GetMapping("/callback")
    public RedirectView handleGoogleCallback(@RequestParam("code") String code) {
        googleFitService.exchangeCodeForTokens(code);
        return new RedirectView("http://localhost:5173/service-hub/google-fit?status=success");
    }
}
