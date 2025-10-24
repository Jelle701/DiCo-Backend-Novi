package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.dto.libre.LluAuthResult;
import com.example_jelle.backenddico.dto.libre.LluLoginRequest;
import com.example_jelle.backenddico.dto.libre.LibreViewSessionResponse;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.ServiceName;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserServiceConnection;
import com.example_jelle.backenddico.repository.UserRepository;
import com.example_jelle.backenddico.repository.UserServiceConnectionRepository;
import com.example_jelle.backenddico.security.CustomUserDetails;
import com.example_jelle.backenddico.service.LibreViewAuthService;
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
@RequestMapping("/libre")
public class LibreController {

    private static final Logger logger = LoggerFactory.getLogger(LibreController.class);

    private final LibreViewAuthService authService;
    private final LibreViewSyncService syncService;
    private final UserRepository userRepository;
    private final UserServiceConnectionRepository connectionRepository;
    private final WebClient libreViewClient;
    private final HashUtil hashUtil;

    public LibreController(LibreViewAuthService authService, LibreViewSyncService syncService, UserRepository userRepository, UserServiceConnectionRepository connectionRepository, WebClient libreViewClient, HashUtil hashUtil) {
        this.authService = authService;
        this.syncService = syncService;
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
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

        logger.info("Successfully authenticated with LibreView for user: {}. Returning session details.", user.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<LibreViewSessionResponse> refreshAuth(@AuthenticationPrincipal CustomUserDetails currentUser) {
        logger.info("Received LibreView refresh request for user: {}", currentUser.getUsername());
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException("Authenticated user not found."));
        UserServiceConnection connection = connectionRepository.findByUserAndServiceName(user, ServiceName.LIBREVIEW)
                .orElseThrow(() -> new IllegalStateException("Cannot refresh: No LibreView connection found for user " + user.getUsername()));

        LluAuthResult authResult = authService.reauthenticateAndStoreToken(connection);
        String accountIdHash = hashUtil.calculateSha256(authResult.userId());
        LibreViewSessionResponse response = new LibreViewSessionResponse(authResult.token(), authResult.userId(), authResult.patientId(), accountIdHash);

        logger.info("Successfully refreshed LibreView session for user: {}.", user.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sync/historical")
    public ResponseEntity<?> triggerHistoricalSync(@AuthenticationPrincipal CustomUserDetails currentUser) {
        logger.info("Received request to trigger historical sync for user: {}", currentUser.getUsername());
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException("Authenticated user not found."));
        new Thread(() -> syncService.importHistoricalDataForUser(user)).start();
        return ResponseEntity.accepted().body(Map.of("message", "Historical data import has been successfully triggered."));
    }

    private Mono<ResponseEntity<String>> proxyRequest(String method, String uri, String token, String accountIdHash) {
        return libreViewClient.method(org.springframework.http.HttpMethod.valueOf(method)).uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("Account-Id", accountIdHash)
                .exchangeToMono(response -> response.toEntity(String.class));
    }

    @GetMapping("/connections")
    public Mono<ResponseEntity<String>> getConnections(@RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections", token, accountIdHash);
    }

    @DeleteMapping("/session")
    public Mono<ResponseEntity<String>> deleteSession(@RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("DELETE", "/llu/session", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/graph")
    public Mono<ResponseEntity<String>> getGraph(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/graph", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/glucose/history")
    public Mono<ResponseEntity<String>> getGlucoseHistory(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/glucose/history", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/devices")
    public Mono<ResponseEntity<String>> getDevices(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/devices", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/family")
    public Mono<ResponseEntity<String>> getFamily(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/family", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/notes")
    public Mono<ResponseEntity<String>> getNotes(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/notes", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/report-settings")
    public Mono<ResponseEntity<String>> getReportSettings(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/report-settings", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/alarm-settings")
    public Mono<ResponseEntity<String>> getAlarmSettings(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/alarm-settings", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/glucose-targets")
    public Mono<ResponseEntity<String>> getGlucoseTargets(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/glucose-targets", token, accountIdHash);
    }

    @GetMapping("/connections/{patientId}/prescriptions")
    public Mono<ResponseEntity<String>> getPrescriptions(@PathVariable String patientId, @RequestHeader("X-LibreView-Token") String token, @RequestHeader("X-LibreView-AccountId") String accountIdHash) {
        return proxyRequest("GET", "/llu/connections/" + patientId + "/prescriptions", token, accountIdHash);
    }
}
