package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import com.example_jelle.backenddico.dto.diabetes.DiabetesSummaryDto;
import com.example_jelle.backenddico.dto.provider.DashboardSummaryDto;
import com.example_jelle.backenddico.dto.provider.DelegatedTokenResponseDto;
import com.example_jelle.backenddico.dto.provider.PatientDashboardSummaryDto;
import com.example_jelle.backenddico.dto.provider.SummaryStatsDto;
import com.example_jelle.backenddico.dto.user.FullUserProfileDto;
import com.example_jelle.backenddico.exception.InvalidAccessException;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.AccessCode;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.Role;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.AccessCodeRepository;
import com.example_jelle.backenddico.repository.GlucoseMeasurementRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import com.example_jelle.backenddico.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderServiceImpl implements ProviderService {

    private static final Logger logger = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private final UserRepository userRepository;
    private final AccessCodeRepository accessCodeRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final GlucoseMeasurementRepository glucoseMeasurementRepository;
    private final DiabetesService diabetesService;

    public ProviderServiceImpl(UserRepository userRepository, AccessCodeRepository accessCodeRepository, UserService userService, JwtUtil jwtUtil, GlucoseMeasurementRepository glucoseMeasurementRepository, DiabetesService diabetesService) {
        this.userRepository = userRepository;
        this.accessCodeRepository = accessCodeRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.glucoseMeasurementRepository = glucoseMeasurementRepository;
        this.diabetesService = diabetesService;
    }

    // ... other methods ...

    @Override
    @Transactional(readOnly = true)
    public List<GlucoseMeasurementDto> getGlucoseMeasurementsForPatient(String requestingUsername, Long patientId) {
        User requestingUser = userRepository.findByUsername(requestingUsername)
                .orElseThrow(() -> new UserNotFoundException("Requesting user not found: " + requestingUsername));

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("Patient not found with ID: " + patientId));

        boolean isLinked = requestingUser.getLinkedPatients().stream().anyMatch(p -> p.getId().equals(patient.getId()));
        if (!isLinked) {
            throw new InvalidAccessException("You are not authorized to view data for this patient.");
        }

        List<GlucoseMeasurement> measurements = glucoseMeasurementRepository.findAllByUser(patient);
        return measurements.stream()
                .map(GlucoseMeasurementDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DiabetesSummaryDto getDiabetesSummaryForPatient(String providerUsername, Long patientId) {
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("Patient not found with ID: " + patientId));

        boolean isLinked = provider.getLinkedPatients().stream().anyMatch(p -> p.getId().equals(patient.getId()));
        if (!isLinked) {
            throw new InvalidAccessException("Provider is not authorized to view data for this patient.");
        }

        return diabetesService.getDiabetesSummary(patient.getUsername());
    }
    
    // --- Unchanged methods below ---

    @Override
    @Transactional
    public void linkPatient(String username, String accessCode) {
        logger.info("Attempting to link patient for user: {} with access code: {}", username, accessCode);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        logger.info("User {} has role: {}", username, user.getRole());

        AccessCode code = accessCodeRepository.findByCodeAndExpirationTimeAfter(accessCode, LocalDateTime.now())
                .orElseThrow(() -> new InvalidAccessException("Access code is invalid or expired."));
        User patient = code.getUser();
        logger.info("Access code is valid and belongs to user: {}", patient.getUsername());

        if (patient.getRole() != Role.PATIENT) {
            throw new InvalidAccessException("The provided access code does not belong to a patient.");
        }

        if (user.getRole() == Role.PROVIDER || user.getRole() == Role.GUARDIAN) {
            logger.info("Linking patient {} to user {}.", patient.getUsername(), user.getUsername());
            user.getLinkedPatients().add(patient);
        } else {
            throw new InvalidAccessException("User does not have the required role to link a patient.");
        }

        userRepository.save(user);
        logger.info("Successfully saved link for user: {}", username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FullUserProfileDto> getLinkedPatients(String providerUsername) {
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        return provider.getLinkedPatients().stream()
                .map(patient -> userService.getFullUserProfile(patient.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public DelegatedTokenResponseDto generateDelegatedToken(String providerUsername, Long patientId) {
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("Patient not found with ID: " + patientId));

        boolean isLinked = provider.getLinkedPatients().stream().anyMatch(p -> p.getId().equals(patient.getId()));
        if (!isLinked) {
            throw new InvalidAccessException("Provider is not linked to this patient.");
        }

        String delegatedToken = jwtUtil.generateDelegatedToken(patient.getUsername());
        return new DelegatedTokenResponseDto(delegatedToken, patient.getUsername());
    }

    @Override
    public DashboardSummaryDto getDashboardSummary(String providerUsername) {
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        List<User> linkedPatients = provider.getLinkedPatients().stream().collect(Collectors.toList());

        int totalPatients = linkedPatients.size();
        int patientsWithHighGlucose = 0;
        int patientsWithLowGlucose = 0;

        return new DashboardSummaryDto(totalPatients, patientsWithHighGlucose, patientsWithLowGlucose);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDashboardSummaryDto getPatientDashboardSummary(String providerUsername, Long patientId) {
        User provider = userRepository.findByUsername(providerUsername)
                .orElseThrow(() -> new UserNotFoundException("Provider not found: " + providerUsername));

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("Patient not found with ID: " + patientId));

        boolean isLinked = provider.getLinkedPatients().stream().anyMatch(p -> p.getId().equals(patient.getId()));
        if (!isLinked) {
            throw new InvalidAccessException("Provider is not linked to this patient.");
        }

        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<GlucoseMeasurement> recentMeasurements = glucoseMeasurementRepository.findByUserAndTimestampAfterOrderByTimestampDesc(patient, sevenDaysAgo);

        if (recentMeasurements.isEmpty()) {
            return new PatientDashboardSummaryDto(new SummaryStatsDto(0, 0), false, null);
        }

        double averageGlucose = recentMeasurements.stream()
                .mapToDouble(GlucoseMeasurement::getValue)
                .average()
                .orElse(0.0);

        long inRangeCount = recentMeasurements.stream()
                .filter(m -> m.getValue() >= 3.9 && m.getValue() <= 10.0)
                .count();
        int timeInRangePercentage = (int) Math.round((double) inRangeCount / recentMeasurements.size() * 100);

        boolean hasAlerts = recentMeasurements.stream()
                .anyMatch(m -> m.getValue() < 3.9 || m.getValue() > 13.9);

        Instant lastSyncTimestamp = recentMeasurements.get(0).getTimestamp().toInstant(ZoneOffset.UTC);

        SummaryStatsDto summaryStats = new SummaryStatsDto(averageGlucose, timeInRangePercentage);

        return new PatientDashboardSummaryDto(summaryStats, hasAlerts, lastSyncTimestamp);
    }
}
