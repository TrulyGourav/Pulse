package com.project.event_ingestion_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
public class EventRequest {

    private UUID eventId = UUID.randomUUID();

    @NotBlank
    private String eventType;

    private Instant timestamp = Instant.now();

    @NotBlank
    private String userId;

    private String source;

    private Map<String, Object> payload;
}
