package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.User;

import java.util.Optional;

public interface UserService {
    String generateAccessCode(String username);
    Optional<String> getAccessCode(String username);
    Optional<User> findByAccessCode(String accessCode);
}
