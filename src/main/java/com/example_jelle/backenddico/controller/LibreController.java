package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.libre.LluAuthResult;
import com.example_jelle.backenddico.dto.libre.LluLoginRequest;
import com.example_jelle.backenddico.dto.libre.LibreViewSessionResponse;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import com.example_jelle.backenddico.security.CustomUserDetails;
import com.example_jelle.backenddico.service.LibreViewAuthService;
import com.example_jelle.backenddico.service.LibreViewDataService;
import com.example_jelle.backenddico.service.LibreViewSyncService;
import com.example_jelle.backenddico.util.HashUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/libre")
public class LibreController {

    private static final Logger logger = LoggerFactory.getLogger(LibreController.class);

    private final LibreViewAuthService authService;
    private final LibreViewDataService dataService;
    private final LibreViewSyncService syncService;
    private final UserRepository userRepository;
    private final WebClient libreViewClient;
    private final HashUtil hashUtil;

    public LibreController(LibreViewAuthService authService, LibreViewDataService dataService, LibreViewSyncService syncService, UserRepository userRepository, WebClient libreViewClient, HashUtil hashUtil) {
        this.authService = authService;
        this.dataService = dataService;
        this.syncService = syncService;
        this.userRepository = userRepository;
        this.libreViewClient = libreViewClient;
        this.hashUtil = hashUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LibreViewSessionResponse> login(@AuthenticationPrincipal CustomUserDetails currentUser, @Valid @RequestBody LluLoginRequest req) {
        logger.info("Received LibreView login request for user: {}", currentUser.getUsername());
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException("Authenticated user not found."));

        LluAuthResult authResult = authService.connectOrUpdateLibreView(user, req.email(), req.password());

        String accountIdHash = hashUtil.calculateSha256(authResult.userId());

        LibreViewSessionResponse response = new LibreViewSessionResponse(authResult.token(), authResult.userId(), authResult.patientId(), accountIdHash);

        logger.info("Successfully authenticated with LibreView for user: {}. Returning session details in response body.", user.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/connections")
    public Mono<ResponseEntity<?>> getConnections(@RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return libreViewClient.get().uri("/llu/connections")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .header("Account-Id", accountIdHash)
            .exchangeToMono(response -> response.toEntity(String.class).map(e -> (ResponseEntity<?>) e));
    }

    @PostMapping("/sync/historical")
    public ResponseEntity<?> triggerHistoricalSync(@AuthenticationPrincipal CustomUserDetails currentUser) {
        logger.info("Received request to trigger historical sync for user: {}", currentUser.getUsername());
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException("Authenticated user not found."));
        new Thread(() -> syncService.importHistoricalDataForUser(user)).start();
        return ResponseEntity.accepted().body(Map.of("message", "Historical data import has been successfully triggered."));
    }
}
