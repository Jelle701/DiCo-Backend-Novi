package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.libre.LluAuthResult;
import com.example_jelle.backenddico.dto.libre.LluConnectionsResponse;
import com.example_jelle.backenddico.dto.libre.LluGraphResponse;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.MeasurementSource;
import com.example_jelle.backenddico.model.ServiceName;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserServiceConnection;
import com.example_jelle.backenddico.repository.GlucoseMeasurementRepository;
import com.example_jelle.backenddico.repository.UserServiceConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LibreViewSyncService {

    private static final Logger logger = LoggerFactory.getLogger(LibreViewSyncService.class);
    private static final int MONTHS_TO_SYNC = 24; // Sync up to 2 years of historical data

    private final LibreViewAuthService libreViewAuthService;
    private final LibreViewDataService libreViewDataService;
    private final GlucoseMeasurementRepository glucoseMeasurementRepository;
    private final UserServiceConnectionRepository connectionRepository;

    public LibreViewSyncService(LibreViewAuthService libreViewAuthService, LibreViewDataService libreViewDataService, GlucoseMeasurementRepository glucoseMeasurementRepository, UserServiceConnectionRepository connectionRepository) {
        this.libreViewAuthService = libreViewAuthService;
        this.libreViewDataService = libreViewDataService;
        this.glucoseMeasurementRepository = glucoseMeasurementRepository;
        this.connectionRepository = connectionRepository;
    }

    @Transactional
    public void importHistoricalDataForUser(User user) {
        logger.info("Starting historical data import for user: {}", user.getUsername());

        UserServiceConnection connection = connectionRepository.findByUserAndServiceName(user, ServiceName.LIBREVIEW)
                .orElseThrow(() -> new IllegalStateException("Cannot start historical sync: No LibreView connection found for user " + user.getUsername()));

        // 1. Ensure we have a valid token
        String token = getValidToken(connection);
        if (token == null) {
            logger.error("Could not obtain a valid LibreView token for user {}. Aborting historical import.", user.getUsername());
            return;
        }

        // 2. Get user's connections
        LluConnectionsResponse patientConnections = libreViewDataService.getConnections(token, connection.getExternalUserId());
        if (patientConnections == null || patientConnections.data() == null || patientConnections.data().isEmpty()) {
            logger.warn("No LibreView patient connections found for user {}. Nothing to import.", user.getUsername());
            return;
        }

        // 3. Loop through each connection and import their historical data
        for (LluConnectionsResponse.ConnectionData patient : patientConnections.data()) {
            logger.info("Importing data for patientId: {} linked to user: {}", patient.patientId(), user.getUsername());

            // 4. Loop backwards month by month
            for (int i = 0; i < MONTHS_TO_SYNC; i++) {
                YearMonth currentMonth = YearMonth.now().minusMonths(i);
                LocalDate startDate = currentMonth.atDay(1);
                LocalDate endDate = currentMonth.atEndOfMonth();

                LluGraphResponse graphData = libreViewDataService.getGraphData(token, connection.getExternalUserId(), patient.patientId(), startDate, endDate);

                if (graphData == null || graphData.data() == null) {
                    logger.warn("Received no graph data for patientId {} for month {}.", patient.patientId(), currentMonth);
                    continue;
                }

                long measurementCount = saveMeasurements(user, graphData);
                logger.info("Saved {} new measurements for user {} from patientId {} for month {}.", measurementCount, user.getUsername(), patient.patientId(), currentMonth);

                if (measurementCount == 0 && i > 6) {
                    logger.info("No new measurements found for month {}. Assuming end of history for patientId {}.", currentMonth, patient.patientId());
                    break;
                }
            }
        }
        connection.setLastSync(ZonedDateTime.now());
        connectionRepository.save(connection);
        logger.info("Finished historical data import for user: {}", user.getUsername());
    }

    private String getValidToken(UserServiceConnection connection) {
        try {
            libreViewDataService.getConnections(connection.getAccessToken(), connection.getExternalUserId());
            logger.debug("Stored LibreView token is valid for user: {}", connection.getUser().getUsername());
            return connection.getAccessToken();
        } catch (WebClientResponseException.Unauthorized e) {
            logger.info("Stored token is expired for user {}. Re-authenticating...", connection.getUser().getUsername());
            LluAuthResult newAuthResult = libreViewAuthService.reauthenticateAndStoreToken(connection);
            return newAuthResult.token();
        } catch (Exception e) {
            logger.error("Failed to validate or refresh token for user: {}", connection.getUser().getUsername(), e);
            return null;
        }
    }

    private long saveMeasurements(User user, LluGraphResponse graphData) {
        return Stream.of(graphData.data().measurement(), graphData.data().amperageMeasurements())
                .flatMap(list -> list == null ? Stream.empty() : list.stream())
                .filter(measurement -> {
                    ZonedDateTime timestamp = measurement.timestamp().atZone(ZoneId.systemDefault());
                    boolean exists = glucoseMeasurementRepository.existsByUserAndTimestampAndSource(
                            user, timestamp, MeasurementSource.LIBREVIEW);
                    if (exists) {
                        logger.trace("Skipping duplicate measurement for user {} at {}", user.getUsername(), timestamp);
                    }
                    return !exists;
                })
                .peek(measurement -> {
                    ZonedDateTime timestamp = measurement.timestamp().atZone(ZoneId.systemDefault());
                    GlucoseMeasurement newMeasurement = new GlucoseMeasurement();
                    newMeasurement.setUser(user);
                    newMeasurement.setTimestamp(timestamp);
                    newMeasurement.setValue(measurement.value());
                    newMeasurement.setSource(MeasurementSource.LIBREVIEW);
                    glucoseMeasurementRepository.save(newMeasurement);
                })
                .count();
    }
}
