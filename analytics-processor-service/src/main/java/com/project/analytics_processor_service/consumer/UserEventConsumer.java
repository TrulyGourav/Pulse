package com.project.analytics_processor_service.consumer;

import com.project.analytics_processor_service.document.UserEventDocument;
import com.project.analytics_processor_service.repository.UserEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserEventRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "user-events",
            groupId = "analytics-processor-group"
    )
    public void consume(String message) {
        try {
            UserEventDocument event =
                    objectMapper.readValue(message, UserEventDocument.class);
            // Idempotency check
            if (repository.existsById(event.getEventId())) {
                return; // already processed
            }
            repository.save(event);
        } catch (Exception e) {
            // Let Spring Kafka handle retry + DLQ
            throw new RuntimeException("Failed to process event", e);
        }
    }
}
