// Repository for User entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Finds a user by their email address.
    Optional<User> findByEmail(String email);

    // Finds a user by their username.
    Optional<User> findByUsername(String username);

    // Fetches all related data needed for FullUserProfileDto in one go using a custom query.
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userProfile LEFT JOIN FETCH u.userDevices LEFT JOIN FETCH u.linkedPatients LEFT JOIN FETCH u.serviceConnections WHERE u.username = :username")
    Optional<User> findByUsernameWithAllDetails(@Param("username") String username);

    // Finds a user by username and eagerly fetches their linked patients.
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.linkedPatients WHERE u.username = :username")
    Optional<User> findByUsernameWithLinkedPatients(@Param("username") String username);

    // Finds a user by their verification code.
    Optional<User> findByVerificationCode(String verificationCode);
}
