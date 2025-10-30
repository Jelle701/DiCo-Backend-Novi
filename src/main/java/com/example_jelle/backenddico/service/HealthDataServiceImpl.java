// Implementation of the HealthDataService interface.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.GlucoseMeasurementDto;
import com.example_jelle.backenddico.dto.health.DailyStepsDto;
import com.example_jelle.backenddico.dto.health.DataPointDto;
import com.example_jelle.backenddico.dto.health.HealthDataRequest;
import com.example_jelle.backenddico.dto.health.HealthDataResponse;
import com.example_jelle.backenddico.exception.UnauthorizedException;
import com.example_jelle.backenddico.model.HealthData;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.HealthDataRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthDataServiceImpl implements HealthDataService {

    private final UserRepository userRepository;
    private final HealthDataRepository healthDataRepository;
    private final GlucoseMeasurementService glucoseMeasurementService;

    // Constructs a new HealthDataServiceImpl.
    public HealthDataServiceImpl(UserRepository userRepository, HealthDataRepository healthDataRepository, GlucoseMeasurementService glucoseMeasurementService) {
        this.userRepository = userRepository;
        this.healthDataRepository = healthDataRepository;
        this.glucoseMeasurementService = glucoseMeasurementService;
    }

    // Saves health data for a user.
    @Override
    public void saveHealthData(String username, HealthDataRequest healthDataRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        if (healthDataRequest.getSteps() != null) {
            healthDataRequest.getSteps().forEach(dataPoint -> {
                HealthData healthData = new HealthData();
                healthData.setUser(user);
                healthData.setDataType("steps");
                healthData.setTimestamp(dataPoint.getTimestamp());
                healthData.setValue(dataPoint.getValue());
                healthDataRepository.save(healthData);
            });
        }

        if (healthDataRequest.getHeartRate() != null) {
            healthDataRequest.getHeartRate().forEach(dataPoint -> {
                HealthData healthData = new HealthData();
                healthData.setUser(user);
                healthData.setDataType("heart_rate");
                healthData.setTimestamp(dataPoint.getTimestamp());
                healthData.setValue(dataPoint.getValue());
                healthDataRepository.save(healthData);
            });
        }
    }

    // Retrieves a summary of health data for a user.
    @Override
    public HealthDataResponse getHealthDataSummary(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        LocalDate today = LocalDate.now();
        List<DailyStepsDto> stepsLast7Days = healthDataRepository.findByUserIdAndDataTypeAndTimestampBetween(
                user.getId(),
                "steps",
                today.minusDays(6).atStartOfDay().toInstant(ZoneOffset.UTC),
                today.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC)
        ).stream()
                .collect(Collectors.groupingBy(hd -> hd.getTimestamp().atZone(ZoneOffset.UTC).toLocalDate(),
                        Collectors.summingDouble(HealthData::getValue)))
                .entrySet().stream()
                .map(entry -> {
                    DailyStepsDto dto = new DailyStepsDto();
                    dto.setDate(entry.getKey());
                    dto.setSteps(entry.getValue().intValue());
                    return dto;
                })
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .collect(Collectors.toList());

        List<HealthData> heartRateData = healthDataRepository.findFirstByUserIdAndDataTypeOrderByTimestampDesc(user.getId(), "heart_rate");
        DataPointDto latestHeartRate = null;
        if (heartRateData != null && !heartRateData.isEmpty()) {
            HealthData latestHealthData = heartRateData.get(0);
            latestHeartRate = new DataPointDto();
            latestHeartRate.setTimestamp(latestHealthData.getTimestamp());
            latestHeartRate.setValue(latestHealthData.getValue());
        }

        List<GlucoseMeasurementDto> glucoseMeasurements = glucoseMeasurementService.getRecentMeasurements(username);

        return new HealthDataResponse(stepsLast7Days, latestHeartRate, glucoseMeasurements);
    }
}
