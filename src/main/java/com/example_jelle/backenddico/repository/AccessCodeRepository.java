package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccessCodeRepository extends JpaRepository<AccessCode, Long> {
    Optional<AccessCode> findByUserIdAndExpirationTimeAfter(Long userId, java.time.LocalDateTime now);
    Optional<AccessCode> findByCodeAndExpirationTimeAfter(String code, java.time.LocalDateTime now);
}
