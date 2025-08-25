package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface is a Spring Data JPA repository for User entities.
 * It provides standard CRUD (Create, Read, Update, Delete) operations for the User entity
 * by extending JpaRepository, as well as custom query methods.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     * @param username The username to search for.
     * @return An Optional containing the User if found, or an empty Optional otherwise.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email address.
     * @param email The email address to search for.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their verification code.
     * @param verificationCode The verification code to search for.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByVerificationCode(String verificationCode);

    /**
     * Checks if a user exists with the given username.
     * @param username The username to check.
     * @return true if a user with the username exists, false otherwise.
     */
    Boolean existsByUsername(String username);

    /**
     * Checks if a user exists with the given email address.
     * @param email The email address to check.
     * @return true if a user with the email exists, false otherwise.
     */
    Boolean existsByEmail(String email);
}
