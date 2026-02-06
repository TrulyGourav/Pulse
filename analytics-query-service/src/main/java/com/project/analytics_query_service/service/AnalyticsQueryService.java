package com.project.analytics_query_service.service;

import com.project.analytics_query_service.document.UserEventDocument;
import com.project.analytics_query_service.repository.UserEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsQueryService {

    private final UserEventRepository repository;

    public List<UserEventDocument> getEventsByType(String eventType) {
        return repository.findByEventType(eventType);
    }

    public List<UserEventDocument> getEventsByUser(String userId) {
        return repository.findByUserId(userId);
    }

    public Iterable<UserEventDocument> getAllEvents() {
        return repository.findAll();
    }
}
