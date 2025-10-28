package com.example_jelle.backenddico.services;

import com.example_jelle.backenddico.exception.CsvValidationException;
import com.example_jelle.backenddico.exception.FileUploadException;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.MeasurementSource;
import com.example_jelle.backenddico.model.Role;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.GlucoseMeasurementRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GlucoseDataService {

    private static final Logger logger = LoggerFactory.getLogger(GlucoseDataService.class);
    private final GlucoseMeasurementRepository glucoseMeasurementRepository;
    private final UserRepository userRepository;

    public GlucoseDataService(GlucoseMeasurementRepository glucoseMeasurementRepository, UserRepository userRepository) {
        this.glucoseMeasurementRepository = glucoseMeasurementRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public int processCsvFile(MultipartFile file) {
        validateFile(file);

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new CsvValidationException("Gebruiker niet gevonden.")); // Should not happen if authenticated

            if (user.getRole() != Role.PATIENT) {
                throw new CsvValidationException("Alleen gebruikers met de rol PATIENT kunnen data uploaden.");
            }

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("Datum", "Tijd", "Glucosewaarde (mmol/L)")
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser csvParser = new CSVParser(reader, csvFormat);

            if (!csvParser.getHeaderNames().containsAll(List.of("Datum", "Tijd", "Glucosewaarde (mmol/L)"))) {
                throw new CsvValidationException("Incorrecte kolomkoppen in CSV. Verwacht: 'Datum,Tijd,Glucosewaarde (mmol/L)'.");
            }

            List<GlucoseMeasurement> measurements = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                measurements.add(parseRecord(csvRecord, user));
            }

            if (measurements.isEmpty()) {
                throw new CsvValidationException("Het CSV-bestand is leeg of bevat geen data.");
            }

            glucoseMeasurementRepository.saveAll(measurements);
            return measurements.size();

        } catch (IOException e) {
            throw new RuntimeException("Fout bij het lezen van het bestand: " + e.getMessage(), e);
        } catch (Exception e) {
            if (e instanceof CsvValidationException || e instanceof FileUploadException) {
                throw e;
            }
            throw new RuntimeException("Fout bij het parsen van het CSV-bestand: " + e.getMessage(), e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileUploadException("Geen bestand meegestuurd.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("text/csv")) {
            throw new FileUploadException("Ongeldig bestandsformaat. Alleen .csv bestanden zijn toegestaan.");
        }
    }

    private GlucoseMeasurement parseRecord(CSVRecord csvRecord, User user) {
        long rowNum = csvRecord.getRecordNumber();
        try {
            String dateStr = csvRecord.get("Datum");
            String timeStr = csvRecord.get("Tijd");
            String valueStr = csvRecord.get("Glucosewaarde (mmol/L)");

            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);
            double value = Double.parseDouble(valueStr);

            if (value < 0) {
                throw new CsvValidationException("Fout op rij " + rowNum + ": Glucosewaarde mag niet negatief zijn.");
            }

            ZonedDateTime timestamp = ZonedDateTime.of(date, time, ZoneId.systemDefault());

            GlucoseMeasurement measurement = new GlucoseMeasurement();
            measurement.setUser(user);
            measurement.setTimestamp(timestamp);
            measurement.setValue(value);
            measurement.setSource(MeasurementSource.MANUAL_UPLOAD);
            return measurement;

        } catch (DateTimeParseException e) {
            throw new CsvValidationException("Fout op rij " + rowNum + ": Ongeldig datum- of tijdformaat. Verwacht 'YYYY-MM-DD' en 'HH:mm'.");
        } catch (NumberFormatException e) {
            throw new CsvValidationException("Fout op rij " + rowNum + ": '" + csvRecord.get("Glucosewaarde (mmol/L)") + "' is geen geldige glucosewaarde.");
        } catch (IllegalArgumentException e) {
            throw new CsvValidationException("Fout op rij " + rowNum + ": Een of meer kolommen ontbreken of zijn onjuist.");
        }
    }

    @Transactional
    public void deleteDataForUser(Long userId) {
        logger.info("Executing deletion of all glucose data records for user ID: {}", userId);
        glucoseMeasurementRepository.deleteByUserId(userId);
    }

    /**
     * Retrieves all glucose measurements for a user based on their username.
     * @param username The username (email) of the user.
     * @return A list of all GlucoseMeasurement objects.
     */
    @Transactional(readOnly = true) // readOnly is more efficient for read operations
    public List<GlucoseMeasurement> findAllByUsername(String username) {
        // 1. Find the user based on the username
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Retrieve all glucose data for the found user ID
        return glucoseMeasurementRepository.findAllByUserIdOrderByTimestampAsc(user.getId());
    }
}
