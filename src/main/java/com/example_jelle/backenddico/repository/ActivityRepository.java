package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findTop30ByOrderByTimestampDesc();
}
