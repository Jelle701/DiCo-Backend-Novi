package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.libre.LluConnectionsResponse;
import com.example_jelle.backenddico.dto.libre.LluGraphResponse;
import com.example_jelle.backenddico.util.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LibreViewDataService {

    private static final Logger logger = LoggerFactory.getLogger(LibreViewDataService.class);
    private final WebClient libreViewClient;
    private final HashUtil hashUtil;

    public LibreViewDataService(WebClient libreViewClient, HashUtil hashUtil) {
        this.libreViewClient = libreViewClient;
        this.hashUtil = hashUtil;
    }

    public LluConnectionsResponse getConnections(String token, String userId) {
        String accountIdHash = hashUtil.calculateSha256(userId);
        logger.info("Fetching connections from LibreView for userId: {} (Hashed Account-Id: {})", userId, accountIdHash);

        return libreViewClient.get()
                .uri("/llu/connections")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("Account-Id", accountIdHash)
                .retrieve()
                .bodyToMono(LluConnectionsResponse.class)
                .block();
    }

    /**
     * Fetches the latest graph data (typically last 2 weeks).
     */
    public LluGraphResponse getGraphData(String token, String userId, String targetPatientId) {
        String accountIdHash = hashUtil.calculateSha256(userId);
        logger.info("Fetching latest graph data from LibreView for targetPatientId: {} (using userId: {} for Account-Id hash)", targetPatientId, userId);

        return libreViewClient.get()
                .uri("/llu/connections/{id}/graph", targetPatientId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("Account-Id", accountIdHash)
                .retrieve()
                .bodyToMono(LluGraphResponse.class)
                .block();
    }

    /**
     * Fetches graph data for a specific date range, required for historical import.
     */
    public LluGraphResponse getGraphData(String token, String userId, String targetPatientId, LocalDate startDate, LocalDate endDate) {
        String accountIdHash = hashUtil.calculateSha256(userId);
        // The API expects the format M/d/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String startDateStr = startDate.format(formatter);
        String endDateStr = endDate.format(formatter);

        logger.info("Fetching historical graph data from LibreView for patientId: {} from {} to {}", targetPatientId, startDateStr, endDateStr);

        String uri = UriComponentsBuilder.fromPath("/llu/connections/{id}/graph")
                .queryParam("startDate", startDateStr)
                .queryParam("endDate", endDateStr)
                .buildAndExpand(targetPatientId)
                .toUriString();

        return libreViewClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("Account-Id", accountIdHash)
                .retrieve()
                .bodyToMono(LluGraphResponse.class)
                .block();
    }
}
