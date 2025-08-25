package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * This interface is a Spring Data JPA repository for HealthData entities.
 * It provides standard CRUD operations and custom JPQL queries for retrieving time-series health data.
 */
@Repository
public interface HealthDataRepository extends JpaRepository<HealthData, Long> {

    /**
     * Finds a list of health data records for a specific user and data type within a given time range.
     * The results are ordered by timestamp in descending order.
     * @param userId The ID of the user.
     * @param dataType The type of data to retrieve (e.g., "steps").
     * @param start The start of the time range (inclusive).
     * @param end The end of the time range (exclusive).
     * @return A list of HealthData entities matching the criteria.
     */
    @Query("SELECT h FROM HealthData h WHERE h.user.id = :userId AND h.dataType = :dataType AND h.timestamp >= :start AND h.timestamp < :end ORDER BY h.timestamp DESC")
    List<HealthData> findByUserIdAndDataTypeAndTimestampBetween(
            Long userId,
            String dataType,
            Instant start,
            Instant end
    );

    /**
     * Finds all health data records for a specific user and data type, ordered by timestamp descending.
     * Note: Despite the name "findFirst", this query returns a list of all matching records.
     * @param userId The ID of the user.
     * @param dataType The type of data to retrieve.
     * @return A list of all HealthData entities for the user and data type, sorted by timestamp.
     */
    @Query("SELECT h FROM HealthData h WHERE h.user.id = :userId AND h.dataType = :dataType ORDER BY h.timestamp DESC")
    List<HealthData> findFirstByUserIdAndDataTypeOrderByTimestampDesc(
            Long userId,
            String dataType
    );

    /**
     * Finds the first health data record that matches a specific user, data type, and exact timestamp.
     * @param userId The ID of the user.
     * @param dataType The type of data.
     * @param timestamp The exact timestamp of the data point.
     * @return An Optional containing the HealthData if found.
     */
    Optional<HealthData> findFirstByUserIdAndDataTypeAndTimestamp(Long userId, String dataType, Instant timestamp);
}
