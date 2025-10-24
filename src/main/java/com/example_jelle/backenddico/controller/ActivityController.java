package com.example_jelle.backenddico.controller;

import com.example_jelle.backenddico.model.Activity;
import com.example_jelle.backenddico.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/activities")
@PreAuthorize("hasRole('ADMIN')")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getRecentActivities() {
        List<Activity> activities = activityService.getRecentActivities();
        return ResponseEntity.ok(activities);
    }
}
