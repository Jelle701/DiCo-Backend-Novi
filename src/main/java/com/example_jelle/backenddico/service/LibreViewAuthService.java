package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.libre.LluAuthResult;
import com.example_jelle.backenddico.dto.libre.LluLoginRequest;
import com.example_jelle.backenddico.dto.libre.LluLoginResponse;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserServiceConnection;
import com.example_jelle.backenddico.repository.UserServiceConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LibreViewAuthService {

    private static final Logger logger = LoggerFactory.getLogger(LibreViewAuthService.class);
    private final WebClient libreViewClient;
    private final UserServiceConnectionRepository connectionRepository;

    public LibreViewAuthService(WebClient libreViewClient, UserServiceConnectionRepository connectionRepository) {
        this.libreViewClient = libreViewClient;
        this.connectionRepository = connectionRepository;
    }

    @Transactional
    public LluAuthResult connectOrUpdateLibreView(User user, String email, String password) {
        logger.info("Creating or updating LibreView connection for user: {}", user.getUsername());

        UserServiceConnection connection = connectionRepository.findByUserAndServiceName(user, com.example_jelle.backenddico.model.ServiceName.LIBREVIEW)
                .orElse(new UserServiceConnection());

        connection.setUser(user);
        connection.setServiceName(com.example_jelle.backenddico.model.ServiceName.LIBREVIEW);
        connection.setEmail(email);
        connection.setPassword(password);

        LluAuthResult authResult = authenticate(email, password);

        connection.setAccessToken(authResult.token());
        connection.setExternalUserId(authResult.userId());
        connection.setExternalPatientId(authResult.patientId());
        connection.setLastSync(LocalDateTime.now());

        connectionRepository.save(connection);
        logger.info("Successfully connected and stored LibreView credentials for user: {}", user.getUsername());

        return authResult;
    }

    @Transactional
    public LluAuthResult reauthenticateAndStoreToken(UserServiceConnection connection) {
        logger.info("Re-authenticating with LibreView for user: {}", connection.getUser().getUsername());
        LluAuthResult authResult = authenticate(connection.getEmail(), connection.getPassword());

        connection.setAccessToken(authResult.token());
        connection.setExternalUserId(authResult.userId());
        connection.setExternalPatientId(authResult.patientId());
        connection.setLastSync(LocalDateTime.now());

        connectionRepository.save(connection);
        logger.info("Successfully re-authenticated and stored new token for user: {}", connection.getUser().getUsername());
        return authResult;
    }

    private LluAuthResult authenticate(String email, String password) {
        LluLoginResponse loginResponse = performLogin(email, password);

        if (loginResponse == null) {
            throw new IllegalStateException("Received no response from LibreView login.");
        }

        if (loginResponse.status() == 0) {
            return extractAuthResult(loginResponse, "login");
        } else if (loginResponse.status() == 4) {
            return handleConsentFlow(loginResponse);
        } else {
            throw new SecurityException("LibreView login failed. Please check credentials. Status: " + loginResponse.status());
        }
    }

    private LluAuthResult handleConsentFlow(LluLoginResponse loginResponse) {
        String consentType = Optional.ofNullable(loginResponse.data()).map(LluLoginResponse.LluData::step).map(LluLoginResponse.Step::type).filter(StringUtils::hasText).orElse("pp");
        String tempToken = Optional.ofNullable(loginResponse.data()).map(LluLoginResponse.LluData::authTicket).map(LluLoginResponse.AuthTicket::token).orElse(null);

        if (tempToken == null) {
            throw new IllegalStateException("LibreView consent flow initiated, but no temporary auth ticket was provided.");
        }

        LluLoginResponse consentResponse = performConsent(consentType, tempToken);
        if (consentResponse == null || consentResponse.status() != 0) {
            throw new RuntimeException("LibreView consent flow failed for type: " + consentType);
        }
        return extractAuthResult(consentResponse, "consent");
    }

    private LluLoginResponse performLogin(String email, String password) {
        LluLoginRequest body = new LluLoginRequest(email, password);
        try {
            return libreViewClient.post().uri("/llu/auth/login").bodyValue(body).retrieve().bodyToMono(LluLoginResponse.class).block(Duration.ofSeconds(15));
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to connect to LibreView. Status: " + e.getStatusCode(), e);
        }
    }

    private LluLoginResponse performConsent(String type, String tempToken) {
        return libreViewClient.post().uri("/llu/auth/continue/{type}", type).header(HttpHeaders.AUTHORIZATION, "Bearer " + tempToken).retrieve().bodyToMono(LluLoginResponse.class).block(Duration.ofSeconds(15));
    }

    private LluAuthResult extractAuthResult(LluLoginResponse response, String context) {
        return Optional.ofNullable(response).map(LluLoginResponse::data).filter(data -> data.authTicket() != null && data.user() != null && data.user().id() != null).map(data -> {
            String userId = data.user().id();
            return new LluAuthResult(data.authTicket().token(), userId, userId);
        }).filter(auth -> auth.token() != null).orElseThrow(() -> new SecurityException("Authentication with LibreView failed. The response did not contain the expected authentication details. Please check your credentials."));
    }
}
