package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface is a Spring Data JPA repository for UserProfile entities.
 * It provides standard CRUD operations and a custom method to find a profile by its API secret.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    /**
     * Finds a user profile by its unique API secret hash.
     * This is used for authenticating requests from external data uploaders like Nightscout.
     * @param apiSecretHash The hashed secret to search for.
     * @return An Optional containing the UserProfile if found.
     */
    Optional<UserProfile> findByApiSecretHash(String apiSecretHash);
}
