// Repository for Activity entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.Activity;
import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // Finds the top 30 activities ordered by timestamp in descending order.
    List<Activity> findTop30ByOrderByTimestampDesc();

    // Nested interface for GlucoseMeasurementRepository (likely an error in original code structure).
    interface GlucoseMeasurementRepository extends JpaRepository<GlucoseMeasurement, Long> {
    }

    // Nested interface for UserRepository (likely an error in original code structure).
    interface UserRepository extends JpaRepository<User, Long> {
        // Finds a user by their email.
        Optional<User> findByEmail(String email);

        // Finds a user by their username.
        Optional<User> findByUsername(String username);

        // Finds a user by username and fetches all related details.
        @Query("SELECT u FROM User u LEFT JOIN FETCH u.userProfile LEFT JOIN FETCH u.userDevices LEFT JOIN FETCH u.linkedPatients LEFT JOIN FETCH u.serviceConnections WHERE u.username = :username")
        Optional<User> findByUsernameWithAllDetails(@Param("username") String username);

        // Finds a user by their verification code.
        Optional<User> findByVerificationCode(String verificationCode);
    }
}
