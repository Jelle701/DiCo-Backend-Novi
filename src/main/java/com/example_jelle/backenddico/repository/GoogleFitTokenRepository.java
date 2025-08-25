package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.GoogleFitToken;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface is a Spring Data JPA repository for GoogleFitToken entities.
 * It provides standard CRUD operations and a custom method to find tokens by user.
 */
@Repository
public interface GoogleFitTokenRepository extends JpaRepository<GoogleFitToken, Long> {
    /**
     * Finds the Google Fit token record associated with a specific user.
     * @param user The user entity to search by.
     * @return An Optional containing the GoogleFitToken if found.
     */
    Optional<GoogleFitToken> findByUser(User user);
}
