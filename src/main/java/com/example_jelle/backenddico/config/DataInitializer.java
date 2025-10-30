// Initializes default data in the database, such as an admin user.
package com.example_jelle.backenddico.config;

import com.example_jelle.backenddico.model.enums.Role;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructs a new DataInitializer.
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Runs the data initialization logic when the application starts.
    @Override
    @Transactional
    public void run(String... args) {
        // Log a valid password hash for 'password' to be used in data.sql
        logger.info("BCrypt hash for 'password': {}", passwordEncoder.encode("password"));

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
