package com.project.analytics_query_service.controller;

import com.project.analytics_query_service.document.UserEventDocument;
import com.project.analytics_query_service.service.AnalyticsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsQueryService service;

    @GetMapping("/events")
    public Iterable<UserEventDocument> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/events/type/{eventType}")
    public List<UserEventDocument> getByEventType(@PathVariable String eventType) {
        return service.getEventsByType(eventType);
    }

    @GetMapping("/events/user/{userId}")
    public List<UserEventDocument> getByUser(@PathVariable String userId) {
        return service.getEventsByUser(userId);
    }
}
