package com.project.event_ingestion_service.kafka.producer;

import com.project.event_ingestion_service.dto.EventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(String topic, EventRequest event) {
        if (!event.getTimestamp().endsWith("Z")) {
            throw new IllegalArgumentException("Timestamp must be ISO-8601 UTC");
        }
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, event.getEventType(), message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish event \n Error: " + e.getMessage());
        }
    }
}
