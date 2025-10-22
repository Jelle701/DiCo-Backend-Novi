package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.diabetes.*;
import com.example_jelle.backenddico.exception.DataNotFoundException;
import com.example_jelle.backenddico.exception.UserNotFoundException;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.MeasurementType;
import com.example_jelle.backenddico.model.MedicalProfile;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.GlucoseMeasurementRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiabetesServiceImpl implements DiabetesService {

    private final UserRepository userRepository;
    private final GlucoseMeasurementRepository glucoseMeasurementRepository;

    public DiabetesServiceImpl(UserRepository userRepository, GlucoseMeasurementRepository glucoseMeasurementRepository) {
        this.userRepository = userRepository;
        this.glucoseMeasurementRepository = glucoseMeasurementRepository;
    }

    @Override
    public DiabetesSummaryDto getDiabetesSummary(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);
        List<GlucoseMeasurement> allMeasurements = glucoseMeasurementRepository.findByUserAndTimestampAfterOrderByTimestampDesc(user, ninetyDaysAgo);

        if (allMeasurements.isEmpty()) {
            throw new DataNotFoundException("No summary data available for this user.");
        }

        DiabetesSummaryDto summary = new DiabetesSummaryDto();

        // Calculate AvgGlucose
        summary.setAvgGlucose(calculateAvgGlucose(allMeasurements));

        // Calculate TimeInRange
        summary.setTimeInRange(calculateTimeInRange(allMeasurements));

        // Calculate CV%
        summary.setCvPct(calculateCvPct(allMeasurements));

        // Get FPG and PPG
        glucoseMeasurementRepository.findFirstByUserAndMeasurementTypeOrderByTimestampDesc(user, MeasurementType.FPG)
                .ifPresent(m -> summary.setFpg(new ValueUnitDto<>(m.getValue(), "mmol/L")));

        glucoseMeasurementRepository.findFirstByUserAndMeasurementTypeOrderByTimestampDesc(user, MeasurementType.PPG)
                .ifPresent(m -> summary.setPpg(new PpgDto(m.getValue(), "mmol/L", 120))); // Assuming 120 minutes

        // Get data from MedicalProfile
        user.getMedicalProfile().ifPresent(profile -> {
            if (profile.getHbA1c() != null) {
                summary.setHbA1c(new ValueUnitDto<>(profile.getHbA1c(), "%"));
            }
            summary.setLifestyle(new LifestyleDto(profile.getCarbsPerDayGrams(), profile.getActivityMinutes(), profile.getSleepHours(), profile.getStressScore()));
            summary.setVitals(new VitalsDto(profile.getSystolic(), profile.getDiastolic(), profile.getWeightKg()));
        });

        // Set updatedAt
        summary.setUpdatedAt(allMeasurements.get(0).getTimestamp().toInstant(ZoneOffset.UTC));

        return summary;
    }

    private AvgGlucoseDto calculateAvgGlucose(List<GlucoseMeasurement> measurements) {
        LocalDateTime now = LocalDateTime.now();
        Double avg7d = calculateAverageForPeriod(measurements, now.minusDays(7));
        Double avg14d = calculateAverageForPeriod(measurements, now.minusDays(14));
        Double avg30d = calculateAverageForPeriod(measurements, now.minusDays(30));
        Double avg90d = calculateAverageForPeriod(measurements, now.minusDays(90));

        AvgGlucoseDto.GlucoseValues values = new AvgGlucoseDto.GlucoseValues(avg7d, avg14d, avg30d, avg90d);
        return new AvgGlucoseDto("mmol/L", values);
    }

    private Double calculateAverageForPeriod(List<GlucoseMeasurement> measurements, LocalDateTime startDate) {
        return measurements.stream()
                .filter(m -> !m.getTimestamp().isBefore(startDate))
                .mapToDouble(GlucoseMeasurement::getValue)
                .average()
                .orElse(0.0);
    }

    private TimeInRangeDto calculateTimeInRange(List<GlucoseMeasurement> measurements) {
        long total = measurements.size();
        if (total == 0) return new TimeInRangeDto(0, 0, 0);

        long below = measurements.stream().filter(m -> m.getValue() < 3.9).count();
        long above = measurements.stream().filter(m -> m.getValue() > 10.0).count();
        long inRange = total - below - above;

        return new TimeInRangeDto(
                (int) Math.round((double) inRange / total * 100),
                (int) Math.round((double) below / total * 100),
                (int) Math.round((double) above / total * 100)
        );
    }

    private Double calculateCvPct(List<GlucoseMeasurement> measurements) {
        if (measurements.size() < 2) return 0.0;

        double mean = measurements.stream().mapToDouble(GlucoseMeasurement::getValue).average().orElse(0.0);
        if (mean == 0) return 0.0;

        double stdDev = Math.sqrt(measurements.stream()
                .mapToDouble(GlucoseMeasurement::getValue)
                .map(v -> (v - mean) * (v - mean))
                .average().orElse(0.0));

        return (stdDev / mean) * 100;
    }
}
