package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This interface is a Spring Data JPA repository for UserDevice entities.
 * It provides standard CRUD operations and a custom method for bulk deletion.
 */
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    /**
     * Deletes all device records associated with a specific user.
     * This operation is transactional and more efficient than fetching all devices
     * and deleting them one by one.
     * @param user The user whose devices should be deleted.
     */
    @Transactional
    void deleteAllByUser(User user);
}
