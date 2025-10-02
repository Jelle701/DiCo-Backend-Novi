package com.example_jelle.backenddico.config;

import com.example_jelle.backenddico.model.Role;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Clean up the old "admin" user if it exists to avoid conflicts
        userRepository.findByUsername("admin").ifPresent(userRepository::delete);

        // Check if the new admin user already exists
        if (userRepository.findByUsername("admin@mail.nl").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin@mail.nl");
            admin.setPassword(passwordEncoder.encode("d1co701"));
            admin.setEmail("admin@mail.nl"); // Email is required
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true); // Admin account should be enabled by default
            userRepository.save(admin);
        }
    }
}
