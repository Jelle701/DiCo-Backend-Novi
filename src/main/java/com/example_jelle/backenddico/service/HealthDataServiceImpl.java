package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.health.DailyStepsDto;
import com.example_jelle.backenddico.dto.health.DataPointDto;
import com.example_jelle.backenddico.dto.health.HealthDataRequest;
import com.example_jelle.backenddico.dto.health.HealthDataResponse;
import com.example_jelle.backenddico.exception.UnauthorizedException;
import com.example_jelle.backenddico.model.HealthData;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.HealthDataRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service implements the logic for saving and retrieving user health data.
 * It handles different types of health data, such as steps and heart rate,
 * and provides a summary of recent activity.
 */
@Service
@AllArgsConstructor
public class HealthDataServiceImpl implements HealthDataService {

    private final UserRepository userRepository;
    private final HealthDataRepository healthDataRepository;

    /**
     * Saves health data points from a request for a specific user.
     * It iterates through the data points (e.g., steps, heart rate) in the request
     * and persists them individually, linked to the user.
     * @param username The username of the user whose data is being saved.
     * @param healthDataRequest The request object containing lists of health data points.
     * @throws UnauthorizedException if the user is not found.
     */
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

    /**
     * Retrieves a summary of health data for a specific user.
     * This includes a 7-day history of daily steps and the latest recorded heart rate.
     * @param username The username of the user.
     * @return A HealthDataResponse DTO containing the summarized data.
     * @throws UnauthorizedException if the user is not found.
     */
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

        return new HealthDataResponse(stepsLast7Days, latestHeartRate);
    }
}
