// Service for processing and managing glucose data.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.exception.CsvValidationException;
import com.example_jelle.backenddico.exception.FileUploadException;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.enums.MeasurementSource;
import com.example_jelle.backenddico.model.enums.Role;
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

    // Constructs a new GlucoseDataService.
    public GlucoseDataService(GlucoseMeasurementRepository glucoseMeasurementRepository, UserRepository userRepository) {
        this.glucoseMeasurementRepository = glucoseMeasurementRepository;
        this.userRepository = userRepository;
    }

    // Processes a CSV file with glucose data.
    @Transactional
    public int processCsvFile(MultipartFile file) {
        validateFile(file);

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new CsvValidationException("User not found.")); // Should not happen if authenticated

            if (user.getRole() != Role.PATIENT) {
                throw new CsvValidationException("Only users with the PATIENT role can upload data.");
            }

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("Datum", "Tijd", "Glucosewaarde (mmol/L)")
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser csvParser = new CSVParser(reader, csvFormat);

            if (!csvParser.getHeaderNames().containsAll(List.of("Datum", "Tijd", "Glucosewaarde (mmol/L)"))) {
                throw new CsvValidationException("Incorrect column headers in CSV. Expected: 'Datum,Tijd,Glucosewaarde (mmol/L)'.");
            }

            List<GlucoseMeasurement> measurements = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                measurements.add(parseRecord(csvRecord, user));
            }

            if (measurements.isEmpty()) {
                throw new CsvValidationException("The CSV file is empty or contains no data.");
            }

            glucoseMeasurementRepository.saveAll(measurements);
            return measurements.size();

        } catch (IOException e) {
            throw new RuntimeException("Error reading the file: " + e.getMessage(), e);
        } catch (Exception e) {
            if (e instanceof CsvValidationException || e instanceof FileUploadException) {
                throw e;
            }
            throw new RuntimeException("Error parsing the CSV file: " + e.getMessage(), e);
        }
    }

    // Validates the uploaded file.
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileUploadException("No file was sent.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("text/csv")) {
            throw new FileUploadException("Invalid file format. Only .csv files are allowed.");
        }
    }

    // Parses a single record from the CSV file.
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
                throw new CsvValidationException("Error on row " + rowNum + ": Glucose value cannot be negative.");
            }

            ZonedDateTime timestamp = ZonedDateTime.of(date, time, ZoneId.systemDefault());

            GlucoseMeasurement measurement = new GlucoseMeasurement();
            measurement.setUser(user);
            measurement.setTimestamp(timestamp);
            measurement.setValue(value);
            measurement.setSource(MeasurementSource.MANUAL_UPLOAD);
            return measurement;

        } catch (DateTimeParseException e) {
            throw new CsvValidationException("Error on row " + rowNum + ": Invalid date or time format. Expected 'YYYY-MM-DD' and 'HH:mm'.");
        } catch (NumberFormatException e) {
            throw new CsvValidationException("Error on row " + rowNum + ": '" + csvRecord.get("Glucosewaarde (mmol/L)") + "' is not a valid glucose value.");
        } catch (IllegalArgumentException e) {
            throw new CsvValidationException("Error on row " + rowNum + ": One or more columns are missing or incorrect.");
        }
    }

    // Deletes all glucose data for a user.
    @Transactional
    public void deleteDataForUser(Long userId) {
        logger.info("Executing deletion of all glucose data records for user ID: {}", userId);
        glucoseMeasurementRepository.deleteByUserId(userId);
    }

    // Retrieves all glucose measurements for a user by their username.
    @Transactional(readOnly = true)
    public List<GlucoseMeasurement> findAllByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return glucoseMeasurementRepository.findAllByUserIdOrderByTimestampAsc(user.getId());
    }
}
