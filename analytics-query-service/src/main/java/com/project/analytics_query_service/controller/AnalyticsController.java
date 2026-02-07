package com.project.analytics_query_service.controller;

import com.project.analytics_query_service.document.UserEventDocument;
import com.project.analytics_query_service.dto.AggregationResponse;
import com.project.analytics_query_service.dto.DateAggregationResponse;
import com.project.analytics_query_service.service.AnalyticsAggregationService;
import com.project.analytics_query_service.service.AnalyticsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsQueryService service;
    private final AnalyticsAggregationService aggregationService;

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

    @GetMapping("/stats/event-types")
    public List<AggregationResponse> countByEventType() {
        return aggregationService.countByEventType();
    }

    @GetMapping("/stats/events-per-day")
    public List<DateAggregationResponse> eventsPerDay() {
        return aggregationService.eventsPerDay();
    }

    @GetMapping("/stats/top-users")
    public List<AggregationResponse> topUsers(
            @RequestParam(defaultValue = "5") int size) {
        return aggregationService.topUsers(size);
    }

}
