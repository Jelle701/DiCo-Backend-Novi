package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.NightscoutEntryDto;
import com.example_jelle.backenddico.exceptions.UnauthorizedException;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.GlucoseMeasurementRepository;
import com.example_jelle.backenddico.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class NightscoutServiceImpl implements NightscoutService {

    private static final Logger logger = LoggerFactory.getLogger(NightscoutServiceImpl.class);
    private static final double MGDL_TO_MMOL_CONVERSION_FACTOR = 18.0182;

    private final UserProfileRepository userProfileRepository;
    private final GlucoseMeasurementRepository glucoseMeasurementRepository;

    public NightscoutServiceImpl(UserProfileRepository userProfileRepository, GlucoseMeasurementRepository glucoseMeasurementRepository) {
        this.userProfileRepository = userProfileRepository;
        this.glucoseMeasurementRepository = glucoseMeasurementRepository;
    }

    @Override
    @Transactional
    public void processEntries(String apiSecret, List<NightscoutEntryDto> entries) {
        if (apiSecret == null || apiSecret.isBlank()) {
            throw new UnauthorizedException("API Secret is missing.");
        }

        String hashedSecret = hashApiSecret(apiSecret);
        User user = userProfileRepository.findByApiSecretHash(hashedSecret)
                .map(profile -> profile.getUser())
                .orElseThrow(() -> new UnauthorizedException("Invalid API Secret."));

        for (NightscoutEntryDto entry : entries) {
            // Convert mg/dL to mmol/L
            double valueInMmol = entry.getSgv() / MGDL_TO_MMOL_CONVERSION_FACTOR;

            // Round to one decimal place
            double roundedValue = Math.round(valueInMmol * 10.0) / 10.0;

            LocalDateTime timestamp = LocalDateTime.ofInstant(entry.getDateAsInstant(), ZoneId.systemDefault());

            // Optional: Check for duplicates to prevent saving the same reading twice
            // if (glucoseMeasurementRepository.existsByUserAndTimestamp(user, timestamp)) {
            //     logger.info("Skipping duplicate entry for user {} at timestamp {}", user.getId(), timestamp);
            //     continue;
            // }

            GlucoseMeasurement measurement = new GlucoseMeasurement();
            measurement.setUser(user);
            measurement.setValue(roundedValue);
            measurement.setTimestamp(timestamp);

            glucoseMeasurementRepository.save(measurement);
        }
        logger.info("Processed {} entries for user {}", entries.size(), user.getEmail());
    }

    private String hashApiSecret(String secret) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] encodedhash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not hash API secret", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}