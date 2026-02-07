package com.project.analytics_processor_service.dlq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DlqReplayConsumer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "user-events-dlq",
            groupId = "dlq-replay-group"
    )
    public void replay(String message) {

        try {
            // 1️⃣ Deserialize failed event
            Map<String, Object> event =
                    objectMapper.readValue(message, Map.class);

            // 2️⃣ (Optional fix) normalize timestamp if needed
            Object ts = event.get("timestamp");
            if (!(ts instanceof String)) {
                event.put("timestamp", Instant.now().toString());
            }

            // 3️⃣ Convert back to JSON
            String fixedEvent = objectMapper.writeValueAsString(event);

            // 4️⃣ Re-publish to main topic
            kafkaTemplate.send("user-events", fixedEvent);

            System.out.println("DLQ replayed event: " + event.get("eventId"));

        } catch (Exception e) {
            // ❌ Last line of defense
            System.err.println("DLQ replay failed permanently: " + e.getMessage());
        }
    }
}
