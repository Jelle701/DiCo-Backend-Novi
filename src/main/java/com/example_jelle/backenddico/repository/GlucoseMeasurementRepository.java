package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.MeasurementSource;
import com.example_jelle.backenddico.model.MeasurementType;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface GlucoseMeasurementRepository extends JpaRepository<GlucoseMeasurement, Long> {
    boolean existsByUserAndTimestampAndSource(User user, ZonedDateTime timestamp, MeasurementSource source);

    List<GlucoseMeasurement> findAllByUser(User user);

    List<GlucoseMeasurement> findByUserAndTimestampAfterOrderByTimestampDesc(User user, ZonedDateTime timestamp);

    Optional<GlucoseMeasurement> findFirstByUserAndMeasurementTypeOrderByTimestampDesc(User user, MeasurementType measurementType);

    void deleteByUserId(Long userId);

    /**
     * Retrieves all glucose measurements for a specific user, sorted by timestamp (oldest first).
     * @param userId The ID of the user.
     * @return A list of GlucoseMeasurement objects.
     */
    List<GlucoseMeasurement> findAllByUserIdOrderByTimestampAsc(Long userId);
}
