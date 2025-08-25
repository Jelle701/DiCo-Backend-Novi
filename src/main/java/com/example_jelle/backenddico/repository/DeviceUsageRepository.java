package com.example_jelle.backenddico.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example_jelle.backenddico.model.DeviceUsage;
import com.example_jelle.backenddico.model.User;

/**
 * This interface is a Spring Data JPA repository for DeviceUsage entities.
 * It provides standard CRUD operations and a custom query method to find device usage records by user.
 */
public interface DeviceUsageRepository extends JpaRepository<DeviceUsage, Long> {
    /**
     * Finds all device usage records associated with a specific user.
     * @param user The user entity to search by.
     * @return A list of DeviceUsage entities for the given user.
     */
    List<DeviceUsage> findByUser(User user);
}
