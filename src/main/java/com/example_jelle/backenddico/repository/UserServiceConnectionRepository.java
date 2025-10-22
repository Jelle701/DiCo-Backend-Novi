package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.ServiceName;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserServiceConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserServiceConnectionRepository extends JpaRepository<UserServiceConnection, Long> {

    /**
     * Finds a specific service connection for a given user and service name.
     *
     * @param user The user.
     * @param serviceName The name of the service (e.g., LIBREVIEW).
     * @return An Optional containing the connection if found.
     */
    Optional<UserServiceConnection> findByUserAndServiceName(User user, ServiceName serviceName);

    /**
     * Finds all service connections for a given user.
     *
     * @param user The user.
     * @return A set of all service connections for the user.
     */
    Set<UserServiceConnection> findByUser(User user);

    /**
     * Finds all users who have a connection for a specific service.
     *
     * @param serviceName The name of the service.
     * @return A list of all connections for that service.
     */
    List<UserServiceConnection> findAllByServiceName(ServiceName serviceName);
}
