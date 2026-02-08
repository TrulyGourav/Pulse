package com.project.analytics_query_service.controller;

import com.project.analytics_query_service.document.UserEventDocument;
import com.project.analytics_query_service.dto.AggregationResponse;
import com.project.analytics_query_service.dto.DateAggregationResponse;
import com.project.analytics_query_service.security.JwtUtil;
import com.project.analytics_query_service.service.AnalyticsAggregationService;
import com.project.analytics_query_service.service.AnalyticsQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsQueryService service;
    private final AnalyticsAggregationService aggregationService;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/events")
    public List<UserEventDocument> getAllEvents() {
        return service.getAllEvents();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/events/user")
    public List<UserEventDocument> getUserAllEvents() {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);
        return service.getUserAllEvents(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/events/type/{eventType}")
    public List<UserEventDocument> getByEventType(@PathVariable String eventType) {
        return service.getEventsByType(eventType);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/events/user/{userId}")
    public List<UserEventDocument> getByUser(@PathVariable String userId) {
        return service.getEventsByUser(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats/event-types")
    public List<AggregationResponse> countByEventType() {
        return aggregationService.countByEventType();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats/events-per-day")
    public List<DateAggregationResponse> eventsPerDay() {
        return aggregationService.eventsPerDay();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats/top-users")
    public List<AggregationResponse> topUsers(
            @RequestParam(defaultValue = "5") int size) {
        return aggregationService.topUsers(size);
    }

}
