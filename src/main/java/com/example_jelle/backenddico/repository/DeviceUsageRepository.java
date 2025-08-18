package com.example_jelle.backenddico.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example_jelle.backenddico.model.DeviceUsage;
import com.example_jelle.backenddico.model.User;

public interface DeviceUsageRepository extends JpaRepository<DeviceUsage, Long> {
    List<DeviceUsage> findByUser(User user);
}
