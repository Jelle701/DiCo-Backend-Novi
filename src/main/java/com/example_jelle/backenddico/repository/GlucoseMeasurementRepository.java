package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

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
}
