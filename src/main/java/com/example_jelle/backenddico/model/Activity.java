// Entity representing an activity.
package com.example_jelle.backenddico.model;

import com.example_jelle.backenddico.model.enums.ActivityType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    private String description;

    private ZonedDateTime timestamp;

    // Default constructor.
    public Activity() {
    }

    // Constructs a new Activity.
    public Activity(ActivityType type, String description) {
        this.type = type;
        this.description = description;
        this.timestamp = ZonedDateTime.now();
    }

    // Gets the activity ID.
    public Long getId() {
        return id;
    }

    // Sets the activity ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the activity type.
    public ActivityType getType() {
        return type;
    }

    // Sets the activity type.
    public void setType(ActivityType type) {
        this.type = type;
    }

    // Gets the activity description.
    public String getDescription() {
        return description;
    }

    // Sets the activity description.
    public void setDescription(String description) {
        this.description = description;
    }

    // Gets the timestamp of the activity.
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    // Sets the timestamp of the activity.
    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
