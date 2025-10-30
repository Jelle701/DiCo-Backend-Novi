// Repository for GlucoseMeasurement entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.enums.MeasurementSource;
import com.example_jelle.backenddico.model.enums.MeasurementType;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface GlucoseMeasurementRepository extends JpaRepository<GlucoseMeasurement, Long> {
    // Checks if a glucose measurement exists for a user at a specific timestamp from a given source.
    boolean existsByUserAndTimestampAndSource(User user, ZonedDateTime timestamp, MeasurementSource source);

    // Finds all glucose measurements for a specific user.
    List<GlucoseMeasurement> findAllByUser(User user);

    // Finds glucose measurements for a user after a specific timestamp, ordered by timestamp descending.
    List<GlucoseMeasurement> findByUserAndTimestampAfterOrderByTimestampDesc(User user, ZonedDateTime timestamp);

    // Finds the first glucose measurement for a user of a specific type, ordered by timestamp descending.
    Optional<GlucoseMeasurement> findFirstByUserAndMeasurementTypeOrderByTimestampDesc(User user, MeasurementType measurementType);

    // Deletes all glucose measurements for a specific user by their ID.
    void deleteByUserId(Long userId);

    // Retrieves all glucose measurements for a specific user, sorted by timestamp (oldest first).
    List<GlucoseMeasurement> findAllByUserIdOrderByTimestampAsc(Long userId);
}
