package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import com.example_jelle.backenddico.exceptions.UserNotFoundException;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.GlucoseMeasurementRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlucoseMeasurementServiceImpl implements GlucoseMeasurementService {

    private final GlucoseMeasurementRepository measurementRepository;
    private final UserRepository userRepository;

    public GlucoseMeasurementServiceImpl(GlucoseMeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public GlucoseMeasurementDto addMeasurement(String userEmail, GlucoseMeasurementDto measurementDto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Gebruiker met e-mail '" + userEmail + "' niet gevonden."));

        GlucoseMeasurement measurement = measurementDto.toEntity();

        // FIX: Gebruik de helper-methode om de relatie aan beide kanten te synchroniseren.
        // Dit is de meest robuuste manier om de object-grafiek consistent te houden.
        user.addGlucoseMeasurement(measurement);

        // Omdat de User-entiteit CascadeType.ALL heeft, zou userRepository.save(user) ook werken.
        // Het direct opslaan van de nieuwe meting is echter ook correct en expliciet.
        GlucoseMeasurement savedMeasurement = measurementRepository.save(measurement);

        return GlucoseMeasurementDto.fromEntity(savedMeasurement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GlucoseMeasurementDto> getRecentMeasurements(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Gebruiker met e-mail '" + userEmail + "' niet gevonden."));

        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);

        List<GlucoseMeasurement> measurements = measurementRepository.findByUserAndTimestampAfterOrderByTimestampDesc(user, ninetyDaysAgo);

        return measurements.stream()
                .map(GlucoseMeasurementDto::fromEntity)
                .collect(Collectors.toList());
    }
}