package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.DeviceUsage;
import java.util.List;

public interface DeviceUsageService {
    List<DeviceUsage> findByUserEmail(String email);
}
