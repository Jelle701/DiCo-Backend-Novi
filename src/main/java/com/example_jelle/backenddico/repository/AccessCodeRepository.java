// Repository for AccessCode entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.AccessCode;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccessCodeRepository extends JpaRepository<AccessCode, Long> {

    // Finds all active access codes for a specific user that have not yet expired.
    List<AccessCode> findAllByUserAndExpirationTimeAfter(User user, LocalDateTime now);

    // Finds an active access code by its code string, ensuring it has not expired.
    Optional<AccessCode> findByCodeAndExpirationTimeAfter(String code, LocalDateTime now);
}
