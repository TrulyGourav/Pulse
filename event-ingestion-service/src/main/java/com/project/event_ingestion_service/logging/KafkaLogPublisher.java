package com.project.event_ingestion_service.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KafkaLogPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void log(String level, String service, String traceId, String message) {
        try {
            Map<String, Object> log = new HashMap<>();
            log.put("service", service);
            log.put("level", level);
            log.put("traceId", traceId);
            log.put("message", message);
            log.put("timestamp", Instant.now().toString());

            kafkaTemplate.send(
                    "app-logs",
                    objectMapper.writeValueAsString(log)
            );
            System.out.println("Log published to kafka");
        } catch (Exception ignored) {
            // logging must never break business flow
            System.out.println("Error while sending log to Kafka" + ignored.getMessage());
        }
    }
}
