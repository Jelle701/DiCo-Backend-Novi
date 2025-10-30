// Scheduled task for synchronizing data from LibreView.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.libre.LluAuthResult;
import com.example_jelle.backenddico.dto.libre.LluConnectionsResponse;
import com.example_jelle.backenddico.dto.libre.LluGraphResponse;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.enums.MeasurementSource;
import com.example_jelle.backenddico.model.enums.ServiceName;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserServiceConnection;
import com.example_jelle.backenddico.repository.GlucoseMeasurementRepository;
import com.example_jelle.backenddico.repository.UserServiceConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

@Component
public class LibreViewSyncTask {

    private static final Logger logger = LoggerFactory.getLogger(LibreViewSyncTask.class);

    private final UserServiceConnectionRepository connectionRepository;
    private final LibreViewAuthService libreViewAuthService;
    private final LibreViewDataService libreViewDataService;
    private final GlucoseMeasurementRepository glucoseMeasurementRepository;

    // Constructs a new LibreViewSyncTask.
    public LibreViewSyncTask(UserServiceConnectionRepository connectionRepository,
                             LibreViewAuthService libreViewAuthService,
                             LibreViewDataService libreViewDataService,
                             GlucoseMeasurementRepository glucoseMeasurementRepository) {
        this.connectionRepository = connectionRepository;
        this.libreViewAuthService = libreViewAuthService;
        this.libreViewDataService = libreViewDataService;
        this.glucoseMeasurementRepository = glucoseMeasurementRepository;
    }

    // Synchronizes LibreView data.
    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    @Transactional
    public void syncLibreViewData() {
        logger.info("Starting LibreView data synchronization task.");

        List<UserServiceConnection> connectionsToSync = connectionRepository.findAllByServiceName(ServiceName.LIBREVIEW);

        if (connectionsToSync.isEmpty()) {
            logger.info("No active LibreView connections found. Skipping sync.");
            return;
        }

        logger.info("Found {} LibreView connection(s) to sync.", connectionsToSync.size());

        for (UserServiceConnection connection : connectionsToSync) {
            User user = connection.getUser();
            try {
                // Skip this connection if credentials are not set
                if (!StringUtils.hasText(connection.getEmail()) || !StringUtils.hasText(connection.getPassword())) {
                    logger.info("Skipping sync for user: {} because LibreView credentials are not set.", user.getUsername());
                    continue;
                }

                logger.info("--- Processing connection for user: {} ---", user.getUsername());

                // 1. Try to fetch data with the stored token.
                LluConnectionsResponse patientConnections = fetchDataWithToken(connection);

                // 2. If it fails (likely due to an expired token), re-authenticate.
                if (patientConnections == null) {
                    logger.info("Stored token for user {} is invalid or missing. Attempting re-authentication...", user.getUsername());
                    LluAuthResult newAuthResult = libreViewAuthService.reauthenticateAndStoreToken(connection);
                    logger.info("Re-authentication successful for user {}. Retrying data fetch with new token.", user.getUsername());
                    // Retry fetching data with the new token.
                    patientConnections = fetchDataWithToken(connection);
                }

                if (patientConnections == null || patientConnections.data() == null) {
                    logger.warn("Could not retrieve patient connections for user: {} even after re-authentication attempt. Skipping user.", user.getUsername());
                    continue;
                }

                logger.info("Successfully fetched {} patient connection(s) for user: {}", patientConnections.data().size(), user.getUsername());

                // 3. For each patient connection, fetch and save graph data.
                for (LluConnectionsResponse.ConnectionData patient : patientConnections.data()) {
                    logger.debug("Fetching graph data for patientId: {}", patient.patientId());
                    LluGraphResponse graphData = libreViewDataService.getGraphData(connection.getAccessToken(), connection.getExternalUserId(), patient.patientId());

                    if (graphData == null || graphData.data() == null) {
                        logger.warn("Graph data for patientId {} was null.", patient.patientId());
                        continue;
                    }

                    long measurementCount = (graphData.data().measurement() != null ? graphData.data().measurement().size() : 0);
                    long amperageCount = (graphData.data().amperageMeasurements() != null ? graphData.data().amperageMeasurements().size() : 0);
                    logger.debug("Fetched {} measurements and {} amperage measurements for patientId: {}", measurementCount, amperageCount, patient.patientId());

                    int savedCount = saveMeasurements(user, graphData);

                    if (savedCount > 0) {
                        logger.info("Saved {} new measurements for user {} from patientId {}.", savedCount, user.getUsername(), patient.patientId());
                    }
                }
                connection.setLastSync(ZonedDateTime.now());
                connectionRepository.save(connection);

            } catch (Exception e) {
                logger.error("An unexpected error occurred while syncing data for user: {}", user.getUsername(), e);
            }
        }

        logger.info("LibreView data synchronization task finished.");
    }

    // Fetches data with the stored token.
    private LluConnectionsResponse fetchDataWithToken(UserServiceConnection connection) {
        if (connection.getAccessToken() == null || connection.getExternalUserId() == null) {
            logger.warn("Cannot fetch data for user {}: token or externalUserId is null.", connection.getUser().getUsername());
            return null;
        }
        try {
            logger.debug("Attempting to fetch connections with stored token for user {}.", connection.getUser().getUsername());
            return libreViewDataService.getConnections(connection.getAccessToken(), connection.getExternalUserId());
        } catch (WebClientResponseException.Unauthorized e) {
            logger.warn("Stored LibreView token for user {} is invalid or expired (401 Unauthorized). A new login will be attempted.", connection.getUser().getUsername());
            return null;
        } catch (WebClientResponseException e) {
            logger.error("WebClient error while fetching connections for user {}: {} - {}", connection.getUser().getUsername(), e.getStatusCode(), e.getResponseBodyAsString());
            return null; // Treat other client/server errors as temporary failures
        }
    }

    // Saves the measurements.
    private int saveMeasurements(User user, LluGraphResponse graphData) {
        return Stream.of(graphData.data().measurement(), graphData.data().amperageMeasurements())
            .flatMap(list -> list == null ? Stream.empty() : list.stream())
            .mapToInt(measurement -> {
                ZonedDateTime timestamp = measurement.timestamp().atZone(ZoneId.systemDefault());
                boolean exists = glucoseMeasurementRepository.existsByUserAndTimestampAndSource(
                    user, timestamp, MeasurementSource.LIBREVIEW);

                if (!exists) {
                    GlucoseMeasurement newMeasurement = new GlucoseMeasurement();
                    newMeasurement.setUser(user);
                    newMeasurement.setTimestamp(timestamp);
                    newMeasurement.setValue(measurement.value());
                    newMeasurement.setSource(MeasurementSource.LIBREVIEW);
                    glucoseMeasurementRepository.save(newMeasurement);
                    return 1; // Count as saved
                }
                return 0; // Not saved
            }).sum();
    }
}
