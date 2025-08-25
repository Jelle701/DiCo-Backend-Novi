package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.GoogleFitToken;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.GoogleFitTokenRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * This service implements the logic for handling the Google Fit API integration.
 * It is responsible for exchanging the authorization code for access and refresh tokens and storing them securely.
 */
@Service
public class GoogleFitServiceImpl implements GoogleFitService {

    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String clientSecret;

    private final String REDIRECT_URI = "http://localhost:8000/auth/google/callback";
    private final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    private final RestTemplate restTemplate;
    private final GoogleFitTokenRepository googleFitTokenRepository;
    private final UserRepository userRepository;

    public GoogleFitServiceImpl(RestTemplate restTemplate, GoogleFitTokenRepository googleFitTokenRepository, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.googleFitTokenRepository = googleFitTokenRepository;
        this.userRepository = userRepository;
    }

    /**
     * Exchanges a Google authorization code for access and refresh tokens.
     * It makes a POST request to Google's token endpoint. Upon success, it retrieves the tokens
     * and persists them for the currently authenticated user. It handles both new token creation
     * and updates to existing tokens (e.g., refreshing the access token).
     * @param code The authorization code received from Google's callback.
     */
    @Override
    public void exchangeCodeForTokens(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("redirect_uri", REDIRECT_URI);
        map.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // Use exchange with ParameterizedTypeReference to handle generic types safely
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            String accessToken = (String) responseBody.get("access_token");
            String refreshToken = (String) responseBody.get("refresh_token");

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            GoogleFitToken googleFitToken = googleFitTokenRepository.findByUser(user)
                    .orElse(new GoogleFitToken());

            googleFitToken.setUser(user);
            googleFitToken.setAccessToken(accessToken);
            if (refreshToken != null) {
                googleFitToken.setRefreshToken(refreshToken);
            }

            googleFitTokenRepository.save(googleFitToken);

        } else {
            // TODO: Handle error
            System.err.println("Error exchanging code for tokens: " + response.getStatusCode());
        }
    }
}
