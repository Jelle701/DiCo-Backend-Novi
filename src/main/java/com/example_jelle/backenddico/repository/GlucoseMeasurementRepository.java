package com.example_jelle.backenddico.repository;

import com.example_jelle.backenddico.model.GlucoseMeasurement;
import com.example_jelle.backenddico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GlucoseMeasurementRepository extends JpaRepository<GlucoseMeasurement, Long> {

    /**
     * Haalt alle metingen op voor een specifieke gebruiker die na een bepaald tijdstip
     * zijn vastgelegd, gesorteerd op tijdstip (nieuwste eerst).
     * Spring Data JPA genereert de query automatisch op basis van de naam van de methode.
     *
     * @param user De gebruiker wiens metingen worden opgevraagd.
     * @param afterTimestamp De ondergrens voor het tijdstip (exclusief).
     * @return Een lijst van glucosemetingen.
     */
    List<GlucoseMeasurement> findByUserAndTimestampAfterOrderByTimestampDesc(User user, LocalDateTime afterTimestamp);
}