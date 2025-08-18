package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String generateAccessCode(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessCode = generateUniqueAccessCode();
        user.setAccessCode(accessCode);
        userRepository.save(user);
        return accessCode;
    }

    @Override
    public Optional<String> getAccessCode(String username) {
        return userRepository.findByUsername(username)
                .map(User::getAccessCode);
    }

    @Override
    public Optional<User> findByAccessCode(String accessCode) {
        return userRepository.findByAccessCode(accessCode);
    }

    private String generateUniqueAccessCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // Korte, unieke code
        } while (userRepository.findByAccessCode(code).isPresent()); // Zorg dat de code uniek is
        return code;
    }
}
