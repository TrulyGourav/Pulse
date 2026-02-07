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

    private String timestamp = Instant.now().toString();    // // always store as ISO string for ES

    @NotBlank
    private String userId;

    private String source;

    private Map<String, Object> payload;
}
