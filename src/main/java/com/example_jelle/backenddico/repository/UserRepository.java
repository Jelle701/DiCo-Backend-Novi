package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    // Fetch all related data needed for FullUserProfileDto in one go using a custom query
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userProfile LEFT JOIN FETCH u.userDevices LEFT JOIN FETCH u.linkedPatients LEFT JOIN FETCH u.serviceConnections WHERE u.username = :username")
    Optional<User> findByUsernameWithAllDetails(@Param("username") String username);

    Optional<User> findByVerificationCode(String verificationCode);
}
