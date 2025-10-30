// Repository for HealthData entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface HealthDataRepository extends JpaRepository<HealthData, Long> {

    // Finds a list of health data records for a specific user and data type within a given time range.
    @Query("SELECT h FROM HealthData h WHERE h.user.id = :userId AND h.dataType = :dataType AND h.timestamp >= :start AND h.timestamp < :end ORDER BY h.timestamp DESC")
    List<HealthData> findByUserIdAndDataTypeAndTimestampBetween(
            Long userId,
            String dataType,
            Instant start,
            Instant end
    );

    // Finds all health data records for a specific user and data type, ordered by timestamp descending.
    @Query("SELECT h FROM HealthData h WHERE h.user.id = :userId AND h.dataType = :dataType ORDER BY h.timestamp DESC")
    List<HealthData> findFirstByUserIdAndDataTypeOrderByTimestampDesc(
            Long userId,
            String dataType
    );

    // Finds the first health data record that matches a specific user, data type, and exact timestamp.
    Optional<HealthData> findFirstByUserIdAndDataTypeAndTimestamp(Long userId, String dataType, Instant timestamp);
}
