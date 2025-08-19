package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.requests.RegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    String register(RegisterRequest registerRequest);
    Optional<User> findByUsername(String username);
}
