// Implementation of the GlucoseMeasurementService interface.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.GlucoseMeasurementRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlucoseMeasurementServiceImpl implements GlucoseMeasurementService {

    private final GlucoseMeasurementRepository measurementRepository;
    private final UserRepository userRepository;

    // Constructs a new GlucoseMeasurementServiceImpl.
    public GlucoseMeasurementServiceImpl(GlucoseMeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    // Adds a new glucose measurement for a specific user.
    @Override
    @Transactional
    public GlucoseMeasurementDto addMeasurement(String userEmail, GlucoseMeasurementDto measurementDto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User with email '" + userEmail + "' not found."));

        GlucoseMeasurement measurement = measurementDto.toEntity();

        // Use the helper method to synchronize the relationship on both sides.
        user.addGlucoseMeasurement(measurement);

        GlucoseMeasurement savedMeasurement = measurementRepository.save(measurement);

        return GlucoseMeasurementDto.fromEntity(savedMeasurement);
    }

    // Retrieves the glucose measurements from the last 90 days for a specific user.
    @Override
    @Transactional(readOnly = true)
    public List<GlucoseMeasurementDto> getRecentMeasurements(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User with email '" + userEmail + "' not found."));

        ZonedDateTime ninetyDaysAgo = ZonedDateTime.now().minusDays(90);

        List<GlucoseMeasurement> measurements = measurementRepository.findByUserAndTimestampAfterOrderByTimestampDesc(user, ninetyDaysAgo);

        return measurements.stream()
                .map(GlucoseMeasurementDto::fromEntity)
                .collect(Collectors.toList());
    }
}
