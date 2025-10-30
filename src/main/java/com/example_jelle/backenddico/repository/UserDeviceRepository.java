// Repository for UserDevice entities.
package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    // Deletes all device records associated with a specific user.
    @Transactional
    void deleteAllByUser(User user);
}
