// Repository for UserProfile entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // Finds a user profile by its API secret hash.
    Optional<UserProfile> findByApiSecretHash(String apiSecretHash);
}
