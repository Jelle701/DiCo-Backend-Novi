package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.MeasurementSource;
import com.example_jelle.backenddico.model.MeasurementType;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This interface is a Spring Data JPA repository for GlucoseMeasurement entities.
 * It provides standard CRUD operations and a custom query method to find recent measurements for a user.
 */
public interface GlucoseMeasurementRepository extends JpaRepository<GlucoseMeasurement, Long> {

    /**
     * Finds all measurements for a specific user that were recorded after a given timestamp,
     * sorted by timestamp in descending order (newest first).
     * Spring Data JPA automatically generates the query based on the method name.
     *
     * @param user The user whose measurements are being requested.
     * @param afterTimestamp The lower bound for the timestamp (exclusive).
     * @return A list of glucose measurements.
     */
    List<GlucoseMeasurement> findByUserAndTimestampAfterOrderByTimestampDesc(User user, LocalDateTime afterTimestamp);

    /**
     * Finds all measurements for a specific user.
     *
     * @param user The user whose measurements are being requested.
     * @return A list of all glucose measurements for the user.
     */
    List<GlucoseMeasurement> findAllByUser(User user);

    /**
     * Finds the most recent measurement for a specific user of a given type.
     *
     * @param user The user whose measurement is being requested.
     * @param type The type of measurement to find.
     * @return An Optional containing the most recent measurement if found.
     */
    Optional<GlucoseMeasurement> findFirstByUserAndMeasurementTypeOrderByTimestampDesc(User user, MeasurementType type);

    /**
     * Checks if a measurement exists for a given user, timestamp, and source.
     *
     * @param user The user.
     * @param timestamp The timestamp of the measurement.
     * @param source The source of the measurement.
     * @return true if a matching measurement exists, false otherwise.
     */
    boolean existsByUserAndTimestampAndSource(User user, LocalDateTime timestamp, MeasurementSource source);
}
