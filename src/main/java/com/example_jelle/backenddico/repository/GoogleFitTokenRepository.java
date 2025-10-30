// Repository for GoogleFitToken entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.GoogleFitToken;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoogleFitTokenRepository extends JpaRepository<GoogleFitToken, Long> {
    // Finds the Google Fit token record associated with a specific user.
    Optional<GoogleFitToken> findByUser(User user);
}
