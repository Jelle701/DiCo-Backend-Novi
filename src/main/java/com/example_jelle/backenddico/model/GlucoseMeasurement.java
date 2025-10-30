// Entity representing a single glucose measurement.
package com.example_jelle.backenddico.model;

import com.example_jelle.backenddico.model.enums.MeasurementSource;
import com.example_jelle.backenddico.model.enums.MeasurementType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class GlucoseMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Breaks the infinite loop
    private User user;

    @Column(nullable = false)
    private ZonedDateTime timestamp;

    @Column(nullable = false)
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementSource source;

    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType; // Can be null for older data

}
