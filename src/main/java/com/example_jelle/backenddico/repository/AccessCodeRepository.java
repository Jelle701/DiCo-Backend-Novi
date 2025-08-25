package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * This interface is a Spring Data JPA repository for AccessCode entities.
 * It provides standard CRUD operations and custom query methods for finding valid access codes.
 */
public interface AccessCodeRepository extends JpaRepository<AccessCode, Long> {
    /**
     * Finds an active access code for a specific user ID that has not yet expired.
     * @param userId The ID of the user.
     * @param now The current time to check against the expiration time.
     * @return An Optional containing the AccessCode if a valid one is found.
     */
    Optional<AccessCode> findByUserIdAndExpirationTimeAfter(Long userId, java.time.LocalDateTime now);

    /**
     * Finds an active access code by its code string, ensuring it has not expired.
     * @param code The access code string to search for.
     * @param now The current time to check against the expiration time.
     * @return An Optional containing the AccessCode if a valid one is found.
     */
    Optional<AccessCode> findByCodeAndExpirationTimeAfter(String code, java.time.LocalDateTime now);
}
