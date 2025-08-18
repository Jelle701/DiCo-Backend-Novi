package com.example_jelle.backenddico.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example_jelle.backenddico.model.DeviceUsage;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.DeviceUsageRepository;
import com.example_jelle.backenddico.repository.UserRepository;

@Service
public class DeviceUsageServiceImpl implements DeviceUsageService {
    private final DeviceUsageRepository repository;
    private final UserRepository userRepository;

    public DeviceUsageServiceImpl(DeviceUsageRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<DeviceUsage> findByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return repository.findByUser(user);
    }
}
