// Repository for UserServiceConnection entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.enums.ServiceName;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserServiceConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserServiceConnectionRepository extends JpaRepository<UserServiceConnection, Long> {

    // Finds a specific service connection for a given user and service name.
    Optional<UserServiceConnection> findByUserAndServiceName(User user, ServiceName serviceName);

    // Finds all service connections for a given user.
    Set<UserServiceConnection> findByUser(User user);

    // Finds all users who have a connection for a specific service.
    List<UserServiceConnection> findAllByServiceName(ServiceName serviceName);
}
