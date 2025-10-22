package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.Activity;
import com.example_jelle.backenddico.model.ActivityType;
import com.example_jelle.backenddico.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void createActivity(ActivityType type, String description) {
        Activity activity = new Activity(type, description);
        activityRepository.save(activity);
    }

    public List<Activity> getRecentActivities() {
        return activityRepository.findTop30ByOrderByTimestampDesc();
    }
}
