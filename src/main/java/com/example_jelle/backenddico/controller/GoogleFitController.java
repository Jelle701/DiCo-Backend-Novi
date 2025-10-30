// Controller for handling Google Fit OAuth2 callback.
package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.service.GoogleFitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/auth/google")
public class GoogleFitController {

    private final GoogleFitService googleFitService;

    // Constructs a new GoogleFitController.
    public GoogleFitController(GoogleFitService googleFitService) {
        this.googleFitService = googleFitService;
    }

    // Handles the callback from Google after the user has granted permission.
    @GetMapping("/callback")
    public RedirectView handleGoogleCallback(@RequestParam("code") String code) {
        googleFitService.exchangeCodeForTokens(code);
        return new RedirectView("http://localhost:5173/service-hub/google-fit?status=success");
    }
}
