package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    /**
     * FIX: Add the missing method to delete all devices associated with a specific user.
     * This is more efficient than fetching all devices and deleting them one by one.
     */
    @Transactional
    void deleteAllByUser(User user);
}