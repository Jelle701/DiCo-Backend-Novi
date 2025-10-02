package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.AccessCode;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This interface is a Spring Data JPA repository for AccessCode entities.
 * It provides standard CRUD operations and custom query methods for finding valid access codes.
 */
public interface AccessCodeRepository extends JpaRepository<AccessCode, Long> {

    /**
     * Finds all active access codes for a specific user that have not yet expired.
     * @param user The user entity to search by.
     * @param now The current time to check against the expiration time.
     * @return A list of active AccessCode entities for the given user.
     */
    List<AccessCode> findAllByUserAndExpirationTimeAfter(User user, LocalDateTime now);

    /**
     * Finds an active access code by its code string, ensuring it has not expired.
     * @param code The access code string to search for.
     * @param now The current time to check against the expiration time.
     * @return An Optional containing the AccessCode if a valid one is found.
     */
    Optional<AccessCode> findByCodeAndExpirationTimeAfter(String code, LocalDateTime now);
}
